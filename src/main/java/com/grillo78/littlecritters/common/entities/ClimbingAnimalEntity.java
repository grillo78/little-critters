package com.grillo78.littlecritters.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public abstract class ClimbingAnimalEntity extends SpiderEntity {

    protected ClimbingAnimalEntity(EntityType<? extends SpiderEntity> p_i48568_1_, World p_i48568_2_) {
        super(p_i48568_1_, p_i48568_2_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.availableGoals.clear();
    }
}
