package com.grillo78.littlecritters.common.entities;

import com.grillo78.littlecritters.common.blocks.ModBlocks;
import com.grillo78.littlecritters.common.entities.ants.AntTypes;
import com.grillo78.littlecritters.common.tileentities.AntHouseTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
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

    protected AntEntity(EntityType<? extends SpiderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AntEntity(EntityType<? extends SpiderEntity> type, World worldIn, AntTypes antType, BlockPos home) {
        this(type, worldIn);
        this.antType = antType;
        this.home = home;

        if (antType == AntTypes.QUEEN){
            this.goalSelector.addGoal(0, new CreateAntHouseGoal(this, 0.2D));
        }
        if (antType == AntTypes.WORKER){
            this.goalSelector.addGoal(0, new RecollectLeafFoodGoal(this, 0.2D));
        }
        this.goalSelector.addGoal(0, new RandomWalkingGoal(this, 0.2D));
        aiInitiated = true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
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
    protected SoundEvent getAmbientSound() {
        return null;
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
        if(compound.contains("home"))
            home = NBTUtil.readBlockPos(compound.getCompound("home"));
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
    public void remove() {
        if(antType == AntTypes.WORKER && this.home != null){
            ((AntHouseTileEntity)this.level.getBlockEntity(this.home)).removeWorkerOutside();
        }
        super.remove();
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        readAdditionalSaveData(additionalData.readNbt());
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("antType", antType.name());
        if (home != null){
            compound.put("home", NBTUtil.writeBlockPos(this.home));
        }
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove();
        }
    }

    @Override
    protected void jumpFromGround() {

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
                if (desiredLeaf != null && !desiredLeaf.isAlive()) {
                    desiredLeaf = null;
                }else{
                    double distance = this.ant.position().distanceTo(desiredLeaf.position());
                    if (distance < 0.25) {
                        desiredLeaf.startRiding(ant);
                        goingBack = true;
                    }
                }
            }
            return false;
        }

        @Override
        public void tick() {
            super.tick();
            Vector3d vector = null;
            if(ant.home != null){
                if (!goingBack) {
                    if(desiredLeaf.isAlive())
                        vector = desiredLeaf.position();
                } else {
                    vector = new Vector3d(ant.home.getX()+0.5, ant.home.getY()+0.25, ant.home.getZ()+0.5);
                }
                if(vector != null){
                    ant.navigation.moveTo(vector.x, vector.y, vector.z, this.speedModifier);
                    if(!this.ant.navigation.isInProgress() && !goingBack){
                        this.ant.moveControl.setWantedPosition(vector.x, vector.y, vector.z, this.speedModifier);
                    }
                    if(goingBack && this.ant.navigation.isDone() && this.ant.level.getBlockEntity(this.ant.home) instanceof AntHouseTileEntity){
                        ((AntHouseTileEntity)this.ant.level.getBlockEntity(this.ant.home)).addFood();
                        ((AntHouseTileEntity)this.ant.level.getBlockEntity(this.ant.home)).addWorker();
                        this.ant.kill();
                    }
                }
            }
        }
    }
}
