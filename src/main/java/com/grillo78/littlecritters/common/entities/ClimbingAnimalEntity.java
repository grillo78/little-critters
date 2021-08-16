package com.grillo78.littlecritters.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public abstract class ClimbingAnimalEntity extends AnimalEntity {

    protected ClimbingAnimalEntity(EntityType<? extends AnimalEntity> p_i48568_1_, World p_i48568_2_) {
        super(p_i48568_1_, p_i48568_2_);
    }
}
