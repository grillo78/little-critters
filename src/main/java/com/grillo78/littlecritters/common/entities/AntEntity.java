package com.grillo78.littlecritters.common.entities;

import com.grillo78.littlecritters.common.blocks.ModBlocks;
import com.grillo78.littlecritters.common.entities.ants.AntTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class AntEntity extends ClimbingAnimalEntity implements IEntityAdditionalSpawnData {

    private static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.defineId(SpiderEntity.class, DataSerializers.BYTE);
    private AntTypes antType = AntTypes.QUEEN;
    private BlockPos home;
    private boolean aiInitiated = false;

    protected AntEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AntEntity(EntityType<? extends AnimalEntity> type, World worldIn, AntTypes antType, BlockPos home) {
        this(type, worldIn);
        this.antType = antType;
        this.home = home;

        if (antType == AntTypes.QUEEN){
            this.goalSelector.addGoal(0, new CreateAntHouseGoal(this, 0.2D));
        }
        if (antType == AntTypes.WORKER){
            this.goalSelector.addGoal(0, new RecollectLeafFoodGoal(this, 0.2D));
        }
        aiInitiated = true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    public void setClimbing(boolean p_70839_1_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_70839_1_) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
    }

    public static boolean canSpawn(EntityType<AntEntity> entityTypeIn, IWorld world, SpawnReason reason,
                                   BlockPos blockpos, Random rand) {
        Block block = world.getBlockState(blockpos.below()).getBlock();
        return (block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL
                || block == Blocks.GRASS_BLOCK || block == Blocks.AIR);
    }

    public static AttributeModifierMap.MutableAttribute setAntAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    public AntTypes getAntType() {
        return antType;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 4600;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void push(Entity entityIn) {
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean hurt(DamageSource dmg, float i) {
        if (dmg == DamageSource.IN_WALL) {
            return false;
        }
        return super.hurt(dmg, i);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("antType"))
            antType = AntTypes.valueOf(compound.getString("antType"));
        if(!aiInitiated){
            if (antType == AntTypes.QUEEN){
                this.goalSelector.addGoal(0, new CreateAntHouseGoal(this, 0.2D));
            }
            if (antType == AntTypes.WORKER){
                this.goalSelector.addGoal(0, new RecollectLeafFoodGoal(this, 0.2D));
            }
        }
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        CompoundNBT compound = new CompoundNBT();
        addAdditionalSaveData(compound);
        buffer.writeNbt(compound);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        readAdditionalSaveData(additionalData.readNbt());
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("antType", antType.name());
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

    private static class CreateAntHouseGoal extends Goal {

        private AntEntity ant;
        private double wantedX = 0;
        private double wantedY = 0;
        private double wantedZ = 0;
        private double speedModifier;

        public CreateAntHouseGoal(AntEntity ant, double speedModifier) {
            this.ant = ant;
            this.speedModifier = speedModifier;
        }

        public boolean canUse() {
            boolean canUse = false;
            if (!this.ant.isVehicle()) {
                Vector3d vector3d = this.getPosition();
                if (vector3d != null && (this.ant.level.getBlockState(new BlockPos(vector3d).below()).getBlock() == Blocks.GRASS_BLOCK || this.ant.level.getBlockState(new BlockPos(vector3d).below()).getBlock() == Blocks.DIRT || this.ant.level.getBlockState(new BlockPos(vector3d).below()).getBlock() == Blocks.COARSE_DIRT)) {
                    this.wantedX = vector3d.x;
                    this.wantedY = vector3d.y;
                    this.wantedZ = vector3d.z;
                    canUse = true;
                }
            }
            return canUse;
        }

        @Nullable
        protected Vector3d getPosition() {
            return RandomPositionGenerator.getPos(this.ant, 10, 7);
        }

        public boolean canContinueToUse() {
            boolean canContinue = this.ant.position().distanceTo(new Vector3d(wantedX, wantedY, wantedZ)) < 0.5;
            if (canContinue) {
                this.ant.level.setBlock(new BlockPos(wantedX, wantedY, wantedZ), ModBlocks.ANT_HOUSE.defaultBlockState(), 3);
                this.ant.remove();
            }
            return canContinue;
        }

        public void start() {
            this.ant.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        }

        public void stop() {
            this.ant.getNavigation().stop();
            super.stop();
        }
    }

    private static class RecollectLeafFoodGoal extends Goal {

        private AntEntity ant;
        private FallingLeafEntity desiredLeaf;
        private double speedModifier;
        private boolean goingBack = false;

        public RecollectLeafFoodGoal(AntEntity ant, double speedModifier) {
            this.ant = ant;
            this.speedModifier = speedModifier;
        }

        public boolean canUse() {
            if (!this.ant.isVehicle()) {
                List<FallingLeafEntity> entities = ant.level.getLoadedEntitiesOfClass(FallingLeafEntity.class, ant.getBoundingBox().inflate(20, 20, 20), (entity) -> !entity.isInvisible());
                double lastDistance = 200;
                for (FallingLeafEntity entity : entities) {
                    if (entity.distanceTo(ant) < lastDistance) {
                        desiredLeaf = entity;
                        lastDistance = entity.distanceTo(ant);
                    }
                }
            }
            return desiredLeaf != null;
        }

        public boolean canContinueToUse() {
            if(!goingBack){
                if (desiredLeaf != null && desiredLeaf.removed) {
                    desiredLeaf = null;
                }else{
                    double distance = this.ant.position().distanceTo(desiredLeaf.position());
                    if (distance < 0.25) {
                        desiredLeaf.startRiding(ant);
                        goingBack = true;
                    }
                }
            } else {

            }
            return false;
        }

        @Override
        public void tick() {
            super.tick();
            Vector3d vector;
            if(ant.home != null){
                if (!goingBack) {
                    vector = desiredLeaf.position();
                } else {
                    vector = new Vector3d(ant.home.getX(), ant.home.getY(), ant.home.getZ());
                }
                this.ant.navigation.moveTo(vector.x, vector.y, vector.z, this.speedModifier);
            }
        }
    }
}
