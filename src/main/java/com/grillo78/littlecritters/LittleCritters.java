package com.grillo78.littlecritters;

import com.grillo78.littlecritters.client.entities.renderers.*;
import com.grillo78.littlecritters.common.blocks.ModBlocks;
import com.grillo78.littlecritters.common.entities.ModEntities;
import com.grillo78.littlecritters.common.entities.goal.PlaceEgg;
import com.grillo78.littlecritters.common.items.ModItems;
import com.grillo78.littlecritters.common.tileentities.ModTileEntities;
import com.grillo78.littlecritters.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Mod(LittleCritters.MOD_ID)
public class LittleCritters {
    public static final String MOD_ID = "little_critters";
    private static final Logger LOGGER = LogManager.getLogger();

    public LittleCritters() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        ModTileEntities.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerModel);
            MinecraftForge.EVENT_BUS.addListener(this::onPreEntityRender);
            MinecraftForge.EVENT_BUS.addListener(this::onPostEntityRender);
            MinecraftForge.EVENT_BUS.addListener(this::onFovUpdate);
        });
    }

    private void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
        EntityType.BEE.dimensions = EntitySize.scalable(0.1F, 0.1F);
        EntityType.SILVERFISH.dimensions = EntitySize.scalable(0.1F, 0.1F);
        EntityType.SQUID.dimensions = EntitySize.scalable(0.5F, 0.5F);
        EntityType.SPIDER.dimensions = EntitySize.scalable(0.1F, 0.1F);
        EntityType.CAVE_SPIDER.dimensions = EntitySize.scalable(0.2F, 0.2F);
        event.enqueueWork(() -> {
            ModEntities.initEntityAttributes();
            ModEntities.registerSpawnPlacement();
        });
    }

    private void onBiomeLoad(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.Category.DESERT && event.getCategory() != Biome.Category.OCEAN  && event.getCategory() != Biome.Category.RIVER && event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND)
            event.getSpawns().getSpawner(EntityClassification.AMBIENT).add(new MobSpawnInfo.Spawners(ModEntities.FIRE_FLY_ENTITY, 100, 1, 10));
        if (event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.TAIGA || event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.EXTREME_HILLS || event.getCategory() == Biome.Category.SWAMP || event.getCategory() == Biome.Category.BEACH || event.getCategory() == Biome.Category.JUNGLE || event.getCategory() == Biome.Category.MESA)
            event.getSpawns().getSpawner(EntityClassification.AMBIENT).add(new MobSpawnInfo.Spawners(ModEntities.FLY_ENTITY, 100, 1, 10));
    }

    private void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide) {
            if (event.getEntity().getType() == EntityType.BEE || event.getEntity().getType() == EntityType.SILVERFISH || event.getEntity().getType() == EntityType.SQUID) {
                if (event.getEntity().getType() == EntityType.SILVERFISH) {
                    ((SilverfishEntity) event.getEntity()).targetSelector.availableGoals.clear();
                }
                if (event.getEntity().getType() == EntityType.SPIDER||event.getEntity().getType() == EntityType.CAVE_SPIDER) {
                    ((SpiderEntity) event.getEntity()).targetSelector.availableGoals.clear();
                }
                setAttribute(((LivingEntity) event.getEntity()), "custom_max_health", Attributes.MAX_HEALTH, UUID.fromString("cbc6c4d8-03e2-4e7e-bcdf-fa9266f33195"), -(((CreatureEntity) event.getEntity()).getMaxHealth() - 0.5), AttributeModifier.Operation.ADDITION);
                if (((CreatureEntity) event.getEntity()).getHealth() > 1)
                    ((CreatureEntity) event.getEntity()).setHealth(1F);
            }
            if (event.getEntity().getType() == EntityType.CHICKEN) {
                ((ChickenEntity) event.getEntity()).goalSelector.addGoal(1, new PlaceEgg((ChickenEntity) event.getEntity()));
            }
        }
    }

    private void setAttribute(LivingEntity entity, String name, Attribute attribute, UUID uuid, double amount, AttributeModifier.Operation operation) {
        ModifiableAttributeInstance instance = entity.getAttribute(attribute);

        if (instance != null) {

            AttributeModifier modifier = instance.getModifier(uuid);

            if (modifier == null) {
                modifier = new AttributeModifier(uuid, name, amount, operation);
                instance.addTransientModifier(modifier);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.ANT_HOUSE, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.CHICKEN_NEST, RenderType.cutoutMipped());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FIRE_FLY_ENTITY, new FireFlyRendererFactory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FLY_ENTITY, new FlyRendererFactory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ANT_ENTITY, new AntRendererFactory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FALLING_LEAF, new FallingLeafRendererFactory());
        Minecraft.getInstance().getEntityRenderDispatcher().renderers.put(EntityType.SQUID, new NewSquidRenderer(Minecraft.getInstance().getEntityRenderDispatcher()));
    }

    @OnlyIn(Dist.CLIENT)
    private void onPreEntityRender(RenderLivingEvent.Pre event) {
        if(event.getEntity().getType() == EntityType.BEE
                || event.getEntity().getType() == EntityType.SILVERFISH
                || event.getEntity().getType() == EntityType.SPIDER
                || event.getEntity().getType() == EntityType.CAVE_SPIDER) {
            event.getMatrixStack().pushPose();
            if (event.getEntity().getType() == EntityType.BEE) {
                event.getRenderer().shadowRadius *= 0.05F;
                event.getMatrixStack().scale(0.05F, 0.05F, 0.05F);
            }
            if (event.getEntity().getType() == EntityType.SILVERFISH) {
                event.getRenderer().shadowRadius *= 0.05F;
                event.getMatrixStack().scale(0.1F, 0.1F, 0.1F);
            }
            if (event.getEntity().getType() == EntityType.CAVE_SPIDER) {
                event.getRenderer().shadowRadius *= 0.05F;
                event.getMatrixStack().scale(0.2F, 0.2F, 0.2F);
            }
            if (event.getEntity().getType() == EntityType.SPIDER) {
                event.getRenderer().shadowRadius *= 0.05F;
                event.getMatrixStack().scale(0.1F, 0.1F, 0.1F);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void onPostEntityRender(RenderLivingEvent.Post event) {
        if(event.getEntity().getType() == EntityType.BEE
                || event.getEntity().getType() == EntityType.SILVERFISH
                || event.getEntity().getType() == EntityType.SPIDER
                || event.getEntity().getType() == EntityType.CAVE_SPIDER) {
            event.getMatrixStack().popPose();
        }
    }


    @OnlyIn(Dist.CLIENT)
    public void onFovUpdate(FOVUpdateEvent event) {
        if (event.getEntity().getItemInHand(Hand.MAIN_HAND).getItem() == ModItems.MAG) {
            if (Minecraft.getInstance().options.keyUse.isDown()) {
                event.setNewfov(calculateFov(15F, event.getFov()));
            }
        } else {
            if (event.getEntity().getItemInHand(Hand.OFF_HAND).getItem() == ModItems.MAG) {
                if (Minecraft.getInstance().options.keyUse.isDown()) {
                    event.setNewfov(calculateFov(15F, event.getFov()));
                }
            }
        }
    }

    public void registerModel(ModelRegistryEvent event) {
        ModelLoader.addSpecialModel(new ResourceLocation(MOD_ID, "item/magnifying_glass_2d"));
        ModelLoader.addSpecialModel(new ResourceLocation(MOD_ID, "item/magnifying_glass_3d"));
    }

    public static float calculateFov(float zoom, float fov)
    {
        return (float)Math.atan(Math.tan(fov) / zoom);
    }
}
