package com.grillo78.littlecritters.common.entities.goal;

import com.grillo78.littlecritters.common.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlaceEgg extends RandomWalkingGoal {

    private boolean eggPlaced = false;

    public PlaceEgg(ChickenEntity mob) {
        super(mob, 1.0D);
        setInterval(100);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.mob.getNavigation().isDone()) {
            if (!this.mob.isBaby() && this.mob.getRandom().nextInt(500) < 1) {
                this.mob.spawnAtLocation(Items.EGG);
                eggPlaced = true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.mob.getNavigation().isDone() && !this.mob.isVehicle())
            return true;
        else if (eggPlaced) {
            eggPlaced = false;
            return false;
        } else
            return true;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Nullable
    @Override
    protected Vector3d getPosition() {
        List<BlockPos> positions = new ArrayList<>();
        AtomicBoolean foundNest = new AtomicBoolean(false);

        BlockPos.betweenClosedStream(mob.blockPosition().offset(-10, -10, -10), mob.blockPosition().offset(10, 10, 10)).forEach((bPos) -> {
            BlockState state = mob.level.getBlockState(bPos);
            Block block = state.getBlock();
            if (block == ModBlocks.CHICKEN_NEST && mob.getRandom().nextInt(100) < 10) {
                positions.add(new BlockPos(bPos.getX(), bPos.getY(), bPos.getZ()));
                foundNest.set(true);
            }
        });
        if (foundNest.get()) {
            BlockPos auxPos = positions.get(mob.getRandom().nextInt(positions.size()));
            return new Vector3d(auxPos.getX() + 0.5, auxPos.getY(), auxPos.getZ() + 0.5);
        } else
            return null;
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(mob.getNavigation().createPath(this.wantedX, this.wantedY, this.wantedZ, 0), this.speedModifier);
    }
}
