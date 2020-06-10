package com.grillo78.smallbugs.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CaveSpiderEntity.class)
public abstract class CaveSpiderEntityMixin extends MonsterEntity {

    protected CaveSpiderEntityMixin(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "getStandingEyeHeight(Lnet/minecraft/entity/Pose;Lnet/minecraft/entity/EntitySize;)F", at = @At("RETURN"), cancellable = true)
    private void getStandingEyeHeight(Pose pose, EntitySize size, CallbackInfoReturnable ci) {
        ci.setReturnValue(size.height/2);
    }

    @Inject(method = "registerAttributes", at = @At("RETURN"))
    private void onRegisterAttirbutes(CallbackInfo ci){
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }

}
