package com.grillo78.smallbugs.mixin;

import com.grillo78.smallbugs.entity.Goals.HideInStoneGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SilverfishEntity.class)
public abstract class SilverfishEntityMixin extends MonsterEntity {

    protected SilverfishEntityMixin(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Inject(method = "registerAttributes", at = @At("RETURN"))
    private void onRegisterAttirbutes(CallbackInfo ci){
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"), cancellable = true)
    private void onRegisterGoals(CallbackInfo ci){
        ci.cancel();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(5, new HideInStoneGoal(this));
    }

}
