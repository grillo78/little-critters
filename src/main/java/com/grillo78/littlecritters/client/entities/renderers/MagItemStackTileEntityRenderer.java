package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.client.util.ModRenderTypes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.Random;

public class MagItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {

    private Random random = new Random();

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        super.renderByItem(stack, transformType, matrixStack, buffer, combinedLight, combinedOverlay);
        if (transformType == ItemCameraTransforms.TransformType.GUI) {
            IBakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(LittleCritters.MOD_ID,
                    "item/magnifying_glass_2d"));
            IVertexBuilder vertexBuilder = buffer
                    .getBuffer(RenderType.translucent());
            MatrixStack.Entry entry = matrixStack.last();
            for (int i = 0; i < model.getQuads(null, null, random, EmptyModelData.INSTANCE).size(); i++) {
                vertexBuilder.addVertexData(entry, model.getQuads(null, null, random, EmptyModelData.INSTANCE).get(i), 1, 1, 1, 1, combinedLight, combinedOverlay, true);
            }
        } else {
            matrixStack.pushPose();
            matrixStack.translate(0.25F, 0.5F, 0.5F);
            if (!Minecraft.getInstance().options.keyUse.isDown()) {
               if(transformType.firstPerson()){
                   Matrix3f normal = matrixStack.last().normal();
                   Matrix4f matrix = matrixStack.last().pose();
                   float crop = 0.4F;
                   float size = 0.5F;
                   MainWindow window = Minecraft.getInstance().getWindow();
                   float texU = ((window.getWidth() - window.getHeight() + window.getHeight() * crop * 2.0F) / 2.0F) / window.getWidth();
                   IVertexBuilder builder = buffer.getBuffer(ModRenderTypes.getScreen());
                   builder.vertex(matrix, 0, size, 0).color(1, 1, 1, 1.0F).uv(texU, 1.0F - crop).overlayCoords(combinedOverlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                   builder.vertex(matrix, 0, 0, 0).color(1, 1, 1, 1.0F).uv(texU, crop).overlayCoords(combinedOverlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                   builder.vertex(matrix, size, 0, 0).color(1, 1, 1, 1.0F).uv(1.0F - texU, crop).overlayCoords(combinedOverlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                   builder.vertex(matrix, size, size, 0).color(1, 1, 1, 1.0F).uv(1.0F - texU, 1.0F - crop).overlayCoords(combinedOverlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
               }
                matrixStack.popPose();
                IBakedModel model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(LittleCritters.MOD_ID,
                        "item/magnifying_glass_3d"));
                IVertexBuilder vertexBuilder = buffer
                        .getBuffer(RenderType.entityTranslucent(PlayerContainer.BLOCK_ATLAS));
                MatrixStack.Entry entry = matrixStack.last();
                for (int i = 0; i < model.getQuads(null, null, random, EmptyModelData.INSTANCE).size(); i++) {
                    vertexBuilder.addVertexData(entry, model.getQuads(null, null, random, EmptyModelData.INSTANCE).get(i), 1, 1, 1, 1, combinedLight, combinedOverlay, true);
                }
            }
        }
    }
}
