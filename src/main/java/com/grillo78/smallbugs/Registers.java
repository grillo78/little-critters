package com.grillo78.smallbugs;

import com.grillo78.smallbugs.entity.FlyEntity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

public class Registers {

    @ObjectHolder(Reference.MODID+":fly")
    public static final EntityType<FlyEntity> FLY = null;

}
