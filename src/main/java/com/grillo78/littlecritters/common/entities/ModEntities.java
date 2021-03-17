package com.grillo78.littlecritters.common.entities;

import com.grillo78.littlecritters.LittleCritters;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            LittleCritters.MOD_ID);

    public static final EntityType<FireFlyEntity> FIRE_FLY_ENTITY = register("firefly", EntityType.Builder.<FireFlyEntity>create(FireFlyEntity::new, EntityClassification.AMBIENT).size(0.05F, 0.05F).build(LittleCritters.MOD_ID + ":fire_fly"));
    public static final EntityType<FlyEntity> FLY_ENTITY = register("fly", EntityType.Builder.<FlyEntity>create(FlyEntity::new, EntityClassification.AMBIENT).size(0.05F, 0.05F).build(LittleCritters.MOD_ID + ":fly"));

    public static void initEntityAttributes() {
        GlobalEntityTypeAttributes.put(FIRE_FLY_ENTITY, FireFlyEntity.setFireFlyAttributes().create());
        GlobalEntityTypeAttributes.put(FLY_ENTITY, FlyEntity.setFlyAttributes().create());
    }

    public static void registerSpawnPlacement() {
        EntitySpawnPlacementRegistry.register(FIRE_FLY_ENTITY, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FireFlyEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(FLY_ENTITY, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlyEntity::canSpawn);
    }

    private static <T extends EntityType> T register(String name, T entity) {
        ENTITIES.register(name, () -> entity);
        return entity;
    }
}
