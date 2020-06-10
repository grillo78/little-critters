package com.grillo78.smallbugs.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends MonsterEntity {

    public SpiderEntityMixin(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "getStandingEyeHeight(Lnet/minecraft/entity/Pose;Lnet/minecraft/entity/EntitySize;)F", at = @At("RETURN"), cancellable = true)
    private void getStandingEyeHeight(Pose pose, EntitySize size, CallbackInfoReturnable ci) {
        ci.setReturnValue(size.height/2);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"), cancellable = true)
    private void onRegisterGoals(CallbackInfo ci){
        ci.cancel();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }



    @Inject(method = "registerAttributes", at = @At("RETURN"))
    private void onRegisterAttirbutes(CallbackInfo ci){
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }
}
