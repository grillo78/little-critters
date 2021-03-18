package com.grillo78.littlecritters.mixin;

import com.grillo78.littlecritters.common.entities.FlyEntity;
import com.grillo78.littlecritters.common.entities.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MixinMobEntity {

    @Inject(method = "Lnet/minecraft/entity/MobEntity;canDespawn(D)Z", at = @At("RETURN"), cancellable = true)
    public void isInRangeToRenderDist(CallbackInfoReturnable callbackInfoReturnable) {
        if (((MobEntity)(Object) this).getType() == EntityType.BEE || ((MobEntity)(Object) this).getType() == EntityType.SILVERFISH) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = "Lnet/minecraft/entity/MobEntity;onInitialSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;", at = @At("HEAD"))
    public void onInitialSpawn(CallbackInfoReturnable callbackInfoReturnable){
        if (((MobEntity)(Object) this) instanceof PigEntity || ((MobEntity)(Object) this) instanceof HorseEntity) {
            for (int i = 0; i < 5; i++) {
                FlyEntity entity = new FlyEntity(ModEntities.FLY_ENTITY,((MobEntity)(Object) this).world);
                entity.setPositionAndRotation(((MobEntity)(Object) this).getPosX(),((MobEntity)(Object) this).getPosY(),((MobEntity)(Object) this).getPosZ(),0,0);
                ((MobEntity)(Object) this).world.addEntity(entity);
            }
        }
    }
}
