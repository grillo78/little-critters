package com.grillo78.littlecritters.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MonsterEntity.class)
public abstract class MixinMonsterEntity {

    @Inject(method = "Lnet/minecraft/entity/monster/MonsterEntity;isDespawnPeaceful()Z", at = @At("RETURN"), cancellable = true)
    public void isInRangeToRenderDist(CallbackInfoReturnable callbackInfoReturnable) {
        if (((MonsterEntity)(Object) this).getType() == EntityType.SILVERFISH) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

}
