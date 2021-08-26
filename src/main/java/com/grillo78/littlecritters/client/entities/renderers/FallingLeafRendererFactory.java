package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.common.entities.FallingLeafEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class FallingLeafRendererFactory implements IRenderFactory<FallingLeafEntity> {
    @Override
    public EntityRenderer<? super FallingLeafEntity> createRenderFor(EntityRendererManager manager) {
        return new FallingLeafRenderer(manager);
    }
}
