package com.grillo78.littlecritters.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MixinMobEntity {

    @Inject(method = "Lnet/minecraft/entity/MobEntity;canDespawn(D)Z", at = @At("RETURN"), cancellable = true)
    public void isInRangeToRenderDist(CallbackInfoReturnable callbackInfoReturnable) {
        if (((MobEntity)(Object) this).getType() == EntityType.BEE || ((MobEntity)(Object) this).getType() == EntityType.SILVERFISH) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

}
