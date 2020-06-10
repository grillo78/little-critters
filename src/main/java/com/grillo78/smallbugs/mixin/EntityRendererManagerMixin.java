package com.grillo78.smallbugs.mixin;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRendererManager.class)
public abstract class EntityRendererManagerMixin {

    @Inject(method = "register(Lnet/minecraft/entity/EntityType;Lnet/minecraft/client/renderer/entity/EntityRenderer;)V", at = @At("HEAD"))
    private <T extends Entity> void onRegister(EntityType<T> entityTypeIn, EntityRenderer<? super T> entityRendererIn, CallbackInfo ci){
        String key = entityTypeIn.getRegistryName().getPath();
        switch (key) {
            case "silverfish":
                entityRendererIn.shadowSize = 0.1f;
                break;
            case "spider":
            case "cave_spider":
            case "bee":
                entityRendererIn.shadowSize = 0.25f;
        }
    }
}
