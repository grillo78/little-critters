package com.grillo78.smallbugs.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity {

    protected BeeEntityMixin(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "registerAttributes", at = @At("RETURN"))
    private void onRegisterAttirbutes(CallbackInfo ci){
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }
}
