package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.common.entities.FallingLeafEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class FallingLeafRenderer extends EntityRenderer<FallingLeafEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(LittleCritters.MOD_ID, "textures/entity/falling_leaf.png");

    protected FallingLeafRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public void render(FallingLeafEntity entity, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn) {
        super.render(entity, p_225623_2_, p_225623_3_, matrixStack, buffer, combinedLightIn);
        matrixStack.pushPose();
        Vector3f leafRotation = entity.getLeafRotation();
        matrixStack.translate(0,0.05,0);
        matrixStack.mulPose(new Quaternion(leafRotation.x(), leafRotation.y(), leafRotation.z(), true));
        IVertexBuilder builder = buffer
                .getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
        Matrix4f posMatrix = matrixStack.last().pose();
        Matrix3f normalMatrix = matrixStack.last().normal();
        builder.vertex(posMatrix, 0, 0, 0).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(combinedLightIn).normal(normalMatrix, 0, 1, 0).endVertex();
        builder.vertex(posMatrix, 0, 0, 0.29f).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(combinedLightIn).normal(normalMatrix, 0, 1, 0).endVertex();
        builder.vertex(posMatrix, 0.29f, 0, 0.29f).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(combinedLightIn).normal(normalMatrix, 0, 1, 0).endVertex();
        builder.vertex(posMatrix, 0.29f, 0, 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(combinedLightIn).normal(normalMatrix, 0, 1, 0).endVertex();
        matrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FallingLeafEntity p_110775_1_) {
        return TEXTURE;
    }
}
