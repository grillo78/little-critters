package com.grillo78.smallbugs.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class LivingEntityMixin extends LivingEntity {

    protected LivingEntityMixin(EntityType<? extends LivingEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "canDespawn", at = @At("RETURN"), cancellable = true)
    private void onCanDespawnCheck(double distanceToClosestPlayer, CallbackInfoReturnable ci){
        String key = this.getType().getRegistryName().getPath();
        if(key.equals("spider")||key.equals("cave_spider")||key.equals("silverfish")){
            ci.setReturnValue(false);
        }
    }

}
