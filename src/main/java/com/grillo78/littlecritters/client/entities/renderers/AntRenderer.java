package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.client.entities.model.AntModel;
import com.grillo78.littlecritters.client.entities.model.FireFlyModel;
import com.grillo78.littlecritters.client.util.ModRenderTypes;
import com.grillo78.littlecritters.common.entities.AntEntity;
import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AntRenderer extends MobRenderer<AntEntity, AntModel<AntEntity>> {
    private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LittleCritters.MOD_ID,
            "textures/entity/ant.png");

    public AntRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new AntModel<>(), 0.1F);

    }

    @Override
    protected void scale(AntEntity antEntity, MatrixStack matrixStackIn, float partialTickTime) {
//        switch (antEntity.getAntType()){
//            case QUEEN:
                matrixStackIn.scale(0.01F, 0.01F, 0.01F);
//                break;
//            case WORKER:
//                matrixStackIn.scale(0.02F, 0.02F, 0.02F);
//                break;
//            case MALE:
//                matrixStackIn.scale(0.02F, 0.02F, 0.02F);
//                break;
//        }
    }

    @Override
    public ResourceLocation getTextureLocation(AntEntity antEntity) {
        return RESOURCE_LOCATION;
    }
}