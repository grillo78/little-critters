package com.grillo78.littlecritters.common.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

public class FireFlyEntity extends AnimalEntity implements IFlyingAnimal {

    protected static final DataParameter<Optional<BlockPos>> ATTACHED_BLOCK_POS = EntityDataManager.defineId(FireFlyEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);
    protected static final DataParameter<Direction> ATTACHED_FACE = EntityDataManager.defineId(FireFlyEntity.class, DataSerializers.DIRECTION);
    private int attachCooldown = 0;
    private int attachTicks = 0;

    public FireFlyEntity(EntityType<? extends FireFlyEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.lookControl = new LookController(this);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
        this.setPathfindingMalus(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathNodeType.COCOA, -1.0F);
        this.setPathfindingMalus(PathNodeType.FENCE, -1.0F);
    }



    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new WanderGoal());
        this.goalSelector.addGoal(9, new SwimGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute setFireFlyAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.6D);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance<4600;
    }

    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity entity) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACHED_FACE, Direction.DOWN);
        this.entityData.define(ATTACHED_BLOCK_POS, Optional.empty());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(ATTACHED_FACE, Direction.from3DDataValue(compound.getByte("AttachFace")));
        this.attachCooldown = compound.getInt("AttachCooldown");
        this.attachTicks = compound.getInt("AttachTicks");
        if (compound.contains("APX")) {
            int i = compound.getInt("APX");
            int j = compound.getInt("APY");
            int k = compound.getInt("APZ");
            this.entityData.set(ATTACHED_BLOCK_POS, Optional.of(new BlockPos(i, j, k)));
        } else {
            this.entityData.set(ATTACHED_BLOCK_POS, Optional.empty());
        }
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("AttachFace", (byte) this.entityData.get(ATTACHED_FACE).get3DDataValue());
        BlockPos blockpos = this.getAttachmentPos();
        compound.putInt("AttachCooldown", attachCooldown);
        compound.putInt("AttachTicks", attachTicks);
        if (blockpos != null) {
            compound.putInt("APX", blockpos.getX());
            compound.putInt("APY", blockpos.getY());
            compound.putInt("APZ", blockpos.getZ());
        }
    }

    public Direction getAttachmentFacing() {
        return this.entityData.get(ATTACHED_FACE);
    }

    @Nullable
    public BlockPos getAttachmentPos() {
        BlockPos blockPos = (BlockPos) ((Optional) this.entityData.get(ATTACHED_BLOCK_POS)).orElse(null);
        return blockPos;
    }

    public void setAttachmentPos(@Nullable BlockPos pos) {
        this.entityData.set(ATTACHED_BLOCK_POS, Optional.ofNullable(pos));
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

    protected void jumpInLiquid(ITag<Fluid> fluidTag) {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.01D, 0.0D));
    }

    public void push(Entity entityIn) {
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, 0.5F * this.getEyeHeight(), this.getBbWidth() * 0.2F);
    }

    @Override
    public boolean hurt(DamageSource dmg, float i) {
        if (dmg == DamageSource.IN_WALL) {
            return false;
        }
        this.attachTicks = 0;
        attachCooldown = 1000 + random.nextInt(1500);
        this.entityData.set(ATTACHED_FACE, Direction.DOWN);
        this.setAttachmentPos(null);
        return super.hurt(dmg, i);
    }

    public static boolean canSpawn(EntityType<FireFlyEntity> entityTypeIn, IWorld world, SpawnReason reason,
                                   BlockPos blockpos, Random rand) {
        Block block = world.getBlockState(blockpos.below()).getBlock();
        return (block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL
                || block == Blocks.GRASS_BLOCK || block == Blocks.AIR) && world.getRawBrightness(blockpos, 0) > 12;
    }

    @SuppressWarnings("deprecation")
    public float getWalkTargetValue(BlockPos pos, IWorldReader worldIn) {
        return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean isPushable() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (attachCooldown > 0) {
            attachCooldown--;
        }
        boolean flag = true;
        if (this.getAttachmentPos() == null) {
            attachTicks = 0;
            if (this.horizontalCollision && attachCooldown == 0 && !onGround) {
                attachCooldown = 5;
                Vector3d vec3d = this.getEyePosition(0);
                Vector3d vec3d1 = this.getViewVector(0);
                Vector3d vec3d2 = vec3d.add(vec3d1.x * 1, vec3d1.y * 1, vec3d1.z * 1);
                BlockRayTraceResult rayTraceblock = this.getCommandSenderWorld().clip(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
                if (rayTraceblock != null && rayTraceblock.getLocation() != null) {
                    BlockPos sidePos = rayTraceblock.getBlockPos();
                    if (level.loadedAndEntityCanStandOnFace(sidePos, this, rayTraceblock.getDirection())) {
                        this.setAttachmentPos(sidePos);
                        this.entityData.set(ATTACHED_FACE, rayTraceblock.getDirection().getOpposite());
                        this.setDeltaMovement(0, 0, 0);
                    }
                }
            }
        } else if (flag) {
            BlockPos pos = this.getAttachmentPos();
            if (level.loadedAndEntityCanStandOnFace(pos, this, this.getAttachmentFacing())) {
                attachTicks++;
                attachCooldown = 150;
                this.yBodyRot = 180.0F;
                this.yBodyRotO = 180.0F;
                this.yRot = 180.0F;
                this.yRotO = 180.0F;//FOR TESTING
                this.yHeadRot = 180.0F;
                this.yHeadRotO = 180.0F;
                this.setZza(0.0F);
                this.setDeltaMovement(0, 0, 0);
            } else {
                this.attachTicks = 0;
                this.entityData.set(ATTACHED_FACE, Direction.DOWN);
                this.setAttachmentPos(null);
            }
        }
        if (attachTicks > 1150 && random.nextInt(123) == 0 || this.getAttachmentPos() != null && this.getTarget() != null) {
            this.attachTicks = 0;
            attachCooldown = 1000 + random.nextInt(1500);
            this.entityData.set(ATTACHED_FACE, Direction.DOWN);
            this.setAttachmentPos(null);
        }
    }

    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BEE_LOOP;
    }

    protected boolean makeFlySound() {
        return true;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BEE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BEE_HURT;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.5F;
    }

    protected boolean isMovementNoisy() {
        return false;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    /**
     * Returns new PathNavigateGround instance
     */
    protected PathNavigator createNavigation(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn) {

            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }

            public void tick() {
                if (FireFlyEntity.this.getAttachmentPos() == null) {
                    super.tick();
                }
            }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(false);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    class WanderGoal extends Goal {
        WanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state
         * necessary for execution in this method as well.
         */
        public boolean canUse() {
            return FireFlyEntity.this.navigation.isDone() && FireFlyEntity.this.random.nextInt(10) == 0 && FireFlyEntity.this.getAttachmentPos() == null;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return FireFlyEntity.this.navigation.isInProgress();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Vector3d vector3d = this.getRandomLocation();
            if (vector3d != null) {
                FireFlyEntity.this.navigation.moveTo(FireFlyEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
            }
        }

        @Nullable
        private Vector3d getRandomLocation() {
            Vector3d vector3d;
            vector3d = FireFlyEntity.this.getViewVector(0.0F);
            //4 , 3
            Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(FireFlyEntity.this, 8 , 7, vector3d, (float)Math.PI / 2F, 2, 1);
            return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(FireFlyEntity.this, 8, 4, -2, vector3d, (float)Math.PI / 2F);
        }// 																															1,1,-1
    }

}
