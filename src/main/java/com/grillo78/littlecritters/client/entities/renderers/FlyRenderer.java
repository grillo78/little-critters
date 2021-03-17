package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.client.entities.model.FlyModel;
import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import com.grillo78.littlecritters.common.entities.FlyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlyRenderer extends MobRenderer<FlyEntity, FlyModel<FlyEntity>> {
    private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LittleCritters.MOD_ID,
            "textures/entity/fly.png");

    public FlyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FlyModel<>(), 0.1F);

    }

    public ResourceLocation getEntityTexture(FlyEntity entity) {
        return RESOURCE_LOCATION;
    }

    protected void preRenderCallback(FlyEntity entitylivingbaseIn, MatrixStack matrixStackIn,
                                     float partialTickTime) {
        matrixStackIn.scale(0.05F, 0.05F, 0.05F);
        matrixStackIn.translate(0,-0.1,0);
    }



}