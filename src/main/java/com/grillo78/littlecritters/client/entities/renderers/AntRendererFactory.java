package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.common.entities.AntEntity;
import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class AntRendererFactory implements IRenderFactory<AntEntity> {
    @Override
    public EntityRenderer<? super AntEntity> createRenderFor(EntityRendererManager manager) {
        return new AntRenderer(manager);
    }
}
