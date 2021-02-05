package com.grillo78.littlecritters;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LittleCritters.MOD_ID)
public class LittleCritters
{
    public static final String MOD_ID = "little_critters";
    private static final Logger LOGGER = LogManager.getLogger();

    public LittleCritters() {
        MinecraftForge.EVENT_BUS.addListener(this::onSizeChange);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()-> () -> {
            MinecraftForge.EVENT_BUS.addListener(this::onPreEntityRender);
            MinecraftForge.EVENT_BUS.addListener(this::onPostEntityRender);
        });
    }

    private void onSizeChange(EntityEvent.Size event){
        if(event.getEntity() instanceof BeeEntity){
            event.setNewSize(new EntitySize(0.05f,0.025f, true));
        }
    }

    private void onPreEntityRender(RenderLivingEvent.Pre event){
        event.getMatrixStack().push();
        if(event.getEntity() instanceof BeeEntity){
            event.getRenderer().shadowSize *= 0.05F;
            event.getMatrixStack().scale(0.05F,0.05F,0.05F);
        }
    }

    private void onPostEntityRender(RenderLivingEvent.Post event){
        event.getMatrixStack().pop();
    }

}
