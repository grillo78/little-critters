package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.client.entities.model.SquidModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class NewSquidRenderer extends MobRenderer<SquidEntity, SquidModel<SquidEntity>> {

    private static ResourceLocation ENTITY_TEXTURE = new ResourceLocation(LittleCritters.MOD_ID, "textures/entity/squid.png");

    public NewSquidRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SquidModel(), 0.05F);
    }

    protected void setupRotations(SquidEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, entityLiving.xBodyRotO, entityLiving.xBodyRot);
        float f1 = MathHelper.lerp(partialTicks, entityLiving.zBodyRotO, entityLiving.zBodyRot);
        matrixStackIn.translate(0.0D, 0.D, 0.0D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
        matrixStackIn.scale(0.4F,0.4F,0.4F);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float getBob(SquidEntity livingBase, float partialTicks) {
        return MathHelper.lerp(partialTicks, livingBase.oldTentacleAngle, livingBase.tentacleAngle);
    }

    @Override
    public ResourceLocation getTextureLocation(SquidEntity entity) {
        return ENTITY_TEXTURE;
    }
}
