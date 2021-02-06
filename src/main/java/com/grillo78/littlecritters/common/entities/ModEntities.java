package com.grillo78.littlecritters.common.entities;

import com.grillo78.littlecritters.LittleCritters;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            LittleCritters.MOD_ID);

    public static final RegistryObject<EntityType<FireFlyEntity>> FIRE_FLY_ENTITY = ENTITIES.register("fire_fly",
            () -> EntityType.Builder
                    .<FireFlyEntity>create(FireFlyEntity::new, EntityClassification.MISC).size(0.05F, 0.05F)
                    .build(LittleCritters.MOD_ID + ":fire_fly"));

    public static void initEntityAttributes(){
        GlobalEntityTypeAttributes.put(FIRE_FLY_ENTITY.get(), FireFlyEntity.setFireFlyAttributes().create());
    }

    public static void registerSpawnPlacement() {
        EntitySpawnPlacementRegistry.register(FIRE_FLY_ENTITY.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FireFlyEntity::canSpawn);
    }
}
