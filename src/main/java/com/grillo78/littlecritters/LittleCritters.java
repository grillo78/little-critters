package com.grillo78.littlecritters;

import com.grillo78.littlecritters.client.entities.renderers.FireFlyRendererFactory;
import com.grillo78.littlecritters.client.entities.renderers.FlyRendererFactory;
import com.grillo78.littlecritters.client.entities.renderers.NewSquidRenderer;
import com.grillo78.littlecritters.common.entities.FlyEntity;
import com.grillo78.littlecritters.common.entities.ModEntities;
import com.grillo78.littlecritters.common.items.ModItems;
import com.grillo78.littlecritters.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
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
        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
            MinecraftForge.EVENT_BUS.addListener(this::onPreEntityRender);
            MinecraftForge.EVENT_BUS.addListener(this::onPostEntityRender);
        });
    }

    private void setup(FMLCommonSetupEvent event) {
        PacketHandler.init();
        EntityType.BEE.size = EntitySize.flexible(0.1F, 0.1F);
        EntityType.SILVERFISH.size = EntitySize.flexible(0.1F, 0.1F);
        EntityType.SQUID.size = EntitySize.flexible(0.5F, 0.5F);
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
        if (!event.getWorld().isRemote) {
            if (event.getEntity().getType() == EntityType.BEE || event.getEntity().getType() == EntityType.SILVERFISH || event.getEntity().getType() == EntityType.SQUID) {
                if (event.getEntity().getType() == EntityType.SILVERFISH) {
                    ((SilverfishEntity) event.getEntity()).targetSelector.goals.clear();
                }
                setAttribute(((LivingEntity) event.getEntity()), "custom_max_health", Attributes.MAX_HEALTH, UUID.fromString("cbc6c4d8-03e2-4e7e-bcdf-fa9266f33195"), -(((CreatureEntity) event.getEntity()).getMaxHealth() - 0.5), AttributeModifier.Operation.ADDITION);
                if (((CreatureEntity) event.getEntity()).getHealth() > 1)
                    ((CreatureEntity) event.getEntity()).setHealth(1F);
            }
        }
    }

    private void setAttribute(LivingEntity entity, String name, Attribute attribute, UUID uuid, double amount, AttributeModifier.Operation operation) {
        ModifiableAttributeInstance instance = entity.getAttribute(attribute);

        if (instance != null) {

            AttributeModifier modifier = instance.getModifier(uuid);

            if (modifier == null) {
                modifier = new AttributeModifier(uuid, name, amount, operation);
                instance.applyNonPersistentModifier(modifier);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FIRE_FLY_ENTITY, new FireFlyRendererFactory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FLY_ENTITY, new FlyRendererFactory());
        Minecraft.getInstance().getRenderManager().renderers.put(EntityType.SQUID, new NewSquidRenderer(Minecraft.getInstance().getRenderManager()));
    }

    @OnlyIn(Dist.CLIENT)
    private void onPreEntityRender(RenderLivingEvent.Pre event) {
        event.getMatrixStack().push();
        if (event.getEntity().getType() == EntityType.BEE) {
            event.getRenderer().shadowSize *= 0.05F;
            event.getMatrixStack().scale(0.05F, 0.05F, 0.05F);
        }
        if (event.getEntity().getType() == EntityType.SILVERFISH) {
            event.getRenderer().shadowSize *= 0.05F;
            event.getMatrixStack().scale(0.1F, 0.1F, 0.1F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void onPostEntityRender(RenderLivingEvent.Post event) {
        event.getMatrixStack().pop();
    }

}
