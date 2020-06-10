package com.grillo78.smallbugs.entity.render.factory;

import com.grillo78.smallbugs.entity.FlyEntity;
import com.grillo78.smallbugs.entity.render.FlyRender;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class FlyRenderFactory implements IRenderFactory<FlyEntity> {
    @Override
    public EntityRenderer<? super FlyEntity> createRenderFor(EntityRendererManager manager) {
        return new FlyRender(manager);
    }
}
