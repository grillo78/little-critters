package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class FireFlyRendererFactory implements IRenderFactory<FireFlyEntity> {
    @Override
    public EntityRenderer<? super FireFlyEntity> createRenderFor(EntityRendererManager manager) {
        return new FireFlyRenderer(manager);
    }
}
