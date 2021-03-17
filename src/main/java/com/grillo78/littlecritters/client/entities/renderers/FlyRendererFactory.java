package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import com.grillo78.littlecritters.common.entities.FlyEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class FlyRendererFactory implements IRenderFactory<FlyEntity> {
    @Override
    public EntityRenderer<? super FlyEntity> createRenderFor(EntityRendererManager manager) {
        return new FlyRenderer(manager);
    }
}
