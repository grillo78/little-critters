package com.grillo78.littlecritters.client.entities.renderers;

import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.client.entities.model.FireFlyModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BeeModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SilverfishModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

import java.util.HashMap;

public class AnimalItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {

    private HashMap<String, EntityModel> models;
    private HashMap<String, ResourceLocation> textures;

    public AnimalItemStackTileEntityRenderer() {
        models = new HashMap<>();
        models.put("bee", new BeeModel());
        models.put("silverfish", new SilverfishModel());
        models.put("firefly", new FireFlyModel());

        textures = new HashMap<>();
        textures.put("bee", new ResourceLocation("textures/entity/bee/bee.png"));
        textures.put("silverfish", new ResourceLocation("textures/entity/silverfish.png"));
        textures.put("firefly", new ResourceLocation(LittleCritters.MOD_ID, "textures/entity/firefly.png"));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        super.renderByItem(stack, transformType, matrixStack, buffer, combinedLight, combinedOverlay);
        matrixStack.pushPose();
        switch (transformType) {
            case GUI:
                matrixStack.translate(0.5, 3, 0.5);
                matrixStack.scale(2F, 2F, 2F);
                break;
            default:
                matrixStack.translate(0.5, 1, 0.5);
                matrixStack.scale(0.25F, 0.25F, 0.25F);
        }
        matrixStack.mulPose(new Quaternion(180, 0, 0, true));
        if(stack.hasTag() && stack.getTag().contains("animal") && models.containsKey(stack.getTag().getString("animal")))
            models.get(stack.getTag().getString("animal")).renderToBuffer(matrixStack, buffer.getBuffer(RenderType.entityTranslucent(textures.get(stack.getTag().getString("animal")))), combinedLight, combinedOverlay, 1, 1, 1, 1);
        matrixStack.popPose();
    }
}
