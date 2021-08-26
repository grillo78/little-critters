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

    public static final EntityType<FireFlyEntity> FIRE_FLY_ENTITY = register("firefly", EntityType.Builder.<FireFlyEntity>of(FireFlyEntity::new, EntityClassification.AMBIENT).sized(0.05F, 0.05F).build(LittleCritters.MOD_ID + ":fire_fly"));
    public static final EntityType<FlyEntity> FLY_ENTITY = register("fly", EntityType.Builder.<FlyEntity>of(FlyEntity::new, EntityClassification.AMBIENT).sized(0.05F, 0.05F).build(LittleCritters.MOD_ID + ":fly"));
    public static final EntityType<AntEntity> ANT_ENTITY = register("ant", EntityType.Builder.<AntEntity>of(AntEntity::new, EntityClassification.AMBIENT).sized(0.05F, 0.05F).build(LittleCritters.MOD_ID + ":fly"));
    public static final EntityType<FallingLeafEntity> FALLING_LEAF = register("falling_leaf", EntityType.Builder.<FallingLeafEntity>of(FallingLeafEntity::new, EntityClassification.AMBIENT).sized(0.25F, 0.25F).build(LittleCritters.MOD_ID + ":falling_leaf"));

    public static void initEntityAttributes() {
        GlobalEntityTypeAttributes.put(FIRE_FLY_ENTITY, FireFlyEntity.setFireFlyAttributes().build());
        GlobalEntityTypeAttributes.put(FLY_ENTITY, FlyEntity.setFlyAttributes().build());
        GlobalEntityTypeAttributes.put(ANT_ENTITY, AntEntity.setAntAttributes().build());
    }

    public static void registerSpawnPlacement() {
        EntitySpawnPlacementRegistry.register(FIRE_FLY_ENTITY, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FireFlyEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(FLY_ENTITY, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlyEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(ANT_ENTITY, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AntEntity::canSpawn);
    }

    private static <T extends EntityType> T register(String name, T entity) {
        ENTITIES.register(name, () -> entity);
        return entity;
    }
}
