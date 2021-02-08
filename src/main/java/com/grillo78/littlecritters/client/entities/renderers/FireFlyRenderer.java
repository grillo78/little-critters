package com.grillo78.littlecritters.client.entities.renderers;
import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.client.entities.model.FireFlyModel;
import com.grillo78.littlecritters.client.util.ModRenderTypes;
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
public class FireFlyRenderer extends MobRenderer<FireFlyEntity, FireFlyModel<FireFlyEntity>> {
    private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LittleCritters.MOD_ID,
            "textures/entity/firefly.png");
    private static final ResourceLocation RESOURCE_LOCATION_GLOW = new ResourceLocation(LittleCritters.MOD_ID,
            "textures/entity/firefly_glow.png");

    public FireFlyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FireFlyModel<>(), 0.1F);
        this.addLayer(new AbstractEyesLayer<FireFlyEntity, FireFlyModel<FireFlyEntity>>(this) {
            @Override
            public RenderType getRenderType() {
                return ModRenderTypes.getBumLayer(RESOURCE_LOCATION_GLOW);
            }
        });

    }

    public ResourceLocation getEntityTexture(FireFlyEntity entity) {
        return RESOURCE_LOCATION;
    }

    protected void preRenderCallback(FireFlyEntity entitylivingbaseIn, MatrixStack matrixStackIn,
                                     float partialTickTime) {
        matrixStackIn.scale(0.1F, 0.1F, 0.1F);
    }

    @Override
    protected void applyRotations(FireFlyEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

        switch (entityLiving.getAttachmentFacing()) {
            case DOWN:
            default:
                break;
            case EAST:
                matrixStackIn.translate(0.025F, 0.0F, 0.0F);

                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));
                break;
            case WEST:
                matrixStackIn.translate(-0.025F, 0.0F, 0.0F);
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
                matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(90.0F));

                break;
            case NORTH:
                matrixStackIn.translate(0.0F, 0.0F, -0.025F);
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
                break;
            case SOUTH:
                matrixStackIn.translate(0.0F, 0.0F, 0.025F);
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180.0F));
                break;
            case UP:
                matrixStackIn.translate(0.0F, 0.0F, 0.0F);
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180.0F));
        }
    }


}