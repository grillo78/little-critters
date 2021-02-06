package com.grillo78.littlecritters.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinClientEntity {

    @OnlyIn(Dist.CLIENT)
    @Inject(method = "Lnet/minecraft/entity/Entity;isInRangeToRenderDist(D)Z", at = @At("RETURN"), cancellable = true)
    public void isInRangeToRenderDist(double distance, CallbackInfoReturnable callbackInfoReturnable){
        if(((Entity)(Object)this).world.isRemote){
            if(((Entity)(Object)this).getType() == EntityType.BEE || ((Entity)(Object)this).getType() == EntityType.SILVERFISH){
                callbackInfoReturnable.setReturnValue(distance<4600);
            }
        }
    }

}
