package com.grillo78.littlecritters;

import com.grillo78.littlecritters.client.entities.renderers.FireFlyRendererFactory;
import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import com.grillo78.littlecritters.common.entities.ModEntities;
import com.grillo78.littlecritters.common.items.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
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
        MinecraftForge.EVENT_BUS.addListener(this::onSizeChange);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
            MinecraftForge.EVENT_BUS.addListener(this::onPreEntityRender);
            MinecraftForge.EVENT_BUS.addListener(this::onPostEntityRender);
        });
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModEntities.initEntityAttributes();
            ModEntities.registerSpawnPlacement();
        });
    }

    private void onBiomeLoad(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.TAIGA)
            event.getSpawns().getSpawner(EntityClassification.AMBIENT).add(new MobSpawnInfo.Spawners(ModEntities.FIRE_FLY_ENTITY, 100, 1, 10));
    }

    private void onSizeChange(EntityEvent.Size event) {
        if (event.getEntity().getType() == EntityType.BEE || event.getEntity().getType() == EntityType.SILVERFISH) {
            event.setNewSize(new EntitySize(0.05F, 0.025F, true));
            event.setNewEyeHeight(0.05F);
        }
    }

    private void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity().getType() == EntityType.BEE || event.getEntity().getType() == EntityType.SILVERFISH) {
            if (event.getEntity().getType() == EntityType.SILVERFISH) {
                ((SilverfishEntity) event.getEntity()).targetSelector.goals.clear();
            }
            setAttribute(((LivingEntity) event.getEntity()), "custom_max_health", Attributes.MAX_HEALTH, UUID.fromString("cbc6c4d8-03e2-4e7e-bcdf-fa9266f33195"), -(((CreatureEntity) event.getEntity()).getHealth() - 1), AttributeModifier.Operation.ADDITION);
        }
    }

    private void setAttribute(LivingEntity entity, String name, Attribute attribute, UUID uuid, double amount, AttributeModifier.Operation operation) {
        ModifiableAttributeInstance instance = entity.getAttribute(attribute);

        if (instance == null) {
            return;
        }

        AttributeModifier modifier = instance.getModifier(uuid);

        if (modifier == null) {
            modifier = new AttributeModifier(uuid, name, amount, operation);
            instance.applyPersistentModifier(modifier);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FIRE_FLY_ENTITY, new FireFlyRendererFactory());
    }

    @OnlyIn(Dist.CLIENT)
    private void onPreEntityRender(RenderLivingEvent.Pre event) {
        event.getMatrixStack().push();
        if (event.getEntity().getType() == EntityType.BEE || event.getEntity().getType() == EntityType.SILVERFISH) {
            event.getRenderer().shadowSize *= 0.05F;
            event.getMatrixStack().scale(0.05F, 0.05F, 0.05F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void onPostEntityRender(RenderLivingEvent.Post event) {
        event.getMatrixStack().pop();
    }
}
