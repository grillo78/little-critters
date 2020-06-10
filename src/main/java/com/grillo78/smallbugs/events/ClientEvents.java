package com.grillo78.smallbugs.events;

import com.grillo78.smallbugs.Reference;
import com.grillo78.smallbugs.Registers;
import com.grillo78.smallbugs.entity.render.factory.FlyRenderFactory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(Registers.FLY, new FlyRenderFactory());
    }
}
