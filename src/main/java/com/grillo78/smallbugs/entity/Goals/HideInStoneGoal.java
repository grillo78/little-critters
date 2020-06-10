package com.grillo78.smallbugs.entity.Goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.SilverfishBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.Random;

public class HideInStoneGoal extends RandomWalkingGoal {
    private Direction facing;
    private boolean doMerge;

    public HideInStoneGoal(MonsterEntity silverfishIn) {
        super(silverfishIn, 1.0D, 10);
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        if (this.creature.getAttackTarget() != null) {
            return false;
        } else if (!this.creature.getNavigator().noPath()) {
            return false;
        } else {
            Random random = this.creature.getRNG();
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.creature.world, this.creature) && random.nextInt(10) == 0) {
                this.facing = Direction.random(random);
                BlockPos blockpos = (new BlockPos(this.creature.getPosX(), this.creature.getPosY() + 0.5D, this.creature.getPosZ())).offset(this.facing);
                BlockState blockstate = this.creature.world.getBlockState(blockpos);
                if (SilverfishBlock.canContainSilverfish(blockstate)) {
                    this.doMerge = true;
                    return true;
                }
            }

            this.doMerge = false;
            return super.shouldExecute();
        }
    }
}
