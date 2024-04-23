package com.grillo78.littlecritters.mixin;

import net.minecraft.entity.passive.ChickenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class MixinChickenEntity {

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/ChickenEntity;isChickenJockey()Z"), cancellable = true)
    public void aiStep(CallbackInfo ci) {
        ci.cancel();
    }
}
