package com.grillo78.littlecritters;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Mod(LittleCritters.MOD_ID)
public class LittleCritters
{
    public static final String MOD_ID = "little_critters";
    private static final Logger LOGGER = LogManager.getLogger();

    public LittleCritters() {
        MinecraftForge.EVENT_BUS.addListener(this::onSizeChange);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityConstructing);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()-> () -> {
            MinecraftForge.EVENT_BUS.addListener(this::onPreEntityRender);
            MinecraftForge.EVENT_BUS.addListener(this::onPostEntityRender);
        });
    }

    private void onSizeChange(EntityEvent.Size event){
        if(event.getEntity() instanceof BeeEntity || event.getEntity() instanceof SilverfishEntity){
            event.setNewSize(new EntitySize(0.05f,0.025f, true));
            event.setNewEyeHeight(0.05F);
        }
    }

    private void onPreEntityRender(RenderLivingEvent.Pre event){
        event.getMatrixStack().push();
        if(event.getEntity() instanceof BeeEntity || event.getEntity() instanceof SilverfishEntity){
            event.getRenderer().shadowSize *= 0.05F;
            event.getMatrixStack().scale(0.05F,0.05F,0.05F);
        }
    }

    private void onPostEntityRender(RenderLivingEvent.Post event){
        event.getMatrixStack().pop();
    }

    private void onEntityConstructing(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof BeeEntity || event.getEntity() instanceof SilverfishEntity){
            setAttribute(((LivingEntity)event.getEntity()),"custom_max_health",Attributes.MAX_HEALTH,UUID.fromString("cbc6c4d8-03e2-4e7e-bcdf-fa9266f33195"),-(((CreatureEntity) event.getEntity()).getHealth()-1), AttributeModifier.Operation.ADDITION);
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
}
