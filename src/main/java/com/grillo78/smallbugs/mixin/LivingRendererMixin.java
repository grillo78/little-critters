package com.grillo78.smallbugs.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingRenderer.class)
public abstract class LivingRendererMixin {

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/LivingRenderer;getSwingProgress(Lnet/minecraft/entity/LivingEntity;F)F"
    ))
    private void onRender(LivingEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, CallbackInfo ci) {
        String key = entityIn.getType().getRegistryName().getPath();
        switch (key) {
            case "spider":
            case "cave_spider":
            case "silverfish":
            case "bee":
                matrixStackIn.scale(0.25f,0.25f,0.25f);
        }
    }
}
