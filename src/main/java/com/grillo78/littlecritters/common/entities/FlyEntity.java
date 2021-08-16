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
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class FlyEntity extends AnimalEntity implements IFlyingAnimal {

    public FlyEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new FlyingMovementController(this, 20, true);
        this.lookControl = new LookController(this);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
        this.setPathfindingMalus(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathNodeType.COCOA, -1.0F);
        this.setPathfindingMalus(PathNodeType.FENCE, -1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance<4600;
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
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

    public static boolean canSpawn(EntityType<FlyEntity> entityTypeIn, IWorld world, SpawnReason reason,
                                   BlockPos blockpos, Random rand) {
        Block block = world.getBlockState(blockpos.below()).getBlock();
        return (block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL
                || block == Blocks.GRASS_BLOCK || block == Blocks.AIR);
    }

    public static AttributeModifierMap.MutableAttribute setFlyAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.FLYING_SPEED, 0.6D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FollowEntityGoal(this,1.0D, 0.25F, 10.0F, PigEntity.class));
        this.goalSelector.addGoal(0, new FollowEntityGoal(this,1.0D, 0.25F, 10.0F, HorseEntity.class));
        this.goalSelector.addGoal(1, new WanderGoal());
        this.goalSelector.addGoal(2, new SwimGoal(this));
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    protected PathNavigator createNavigation(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn) {

            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
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
            return FlyEntity.this.navigation.isDone() && FlyEntity.this.random.nextInt(2) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return FlyEntity.this.navigation.isInProgress();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Vector3d vector3d = this.getRandomLocation();
            if (vector3d != null) {
                FlyEntity.this.navigation.moveTo(FlyEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
            }
        }

        @Nullable
        private Vector3d getRandomLocation() {
            Vector3d vector3d;
            vector3d = FlyEntity.this.getViewVector(0.0F);
            Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(FlyEntity.this, 8 , 7, vector3d, (float)Math.PI / 2F, 2, 1);
            return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(FlyEntity.this, 8, 4, -2, vector3d, (float)Math.PI / 2F);
        }
    }

    class FollowEntityGoal extends FollowMobGoal {

        public FollowEntityGoal(MobEntity entity, double speedModifier, float stopDistance, float areaSize, Class clazz) {
            super(entity, speedModifier, stopDistance, areaSize);
            this.followPredicate = (p_210291_1_) -> {
                return p_210291_1_ != null && clazz == p_210291_1_.getClass();
            };
        }

        @Override
        public boolean canUse() {
            return super.canUse() && random.nextBoolean();
        }
    }
}
