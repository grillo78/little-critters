package com.grillo78.littlecritters.mixin;

import com.grillo78.littlecritters.common.entities.FallingLeafEntity;
import com.grillo78.littlecritters.common.entities.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LeavesBlock.class)
public class MixinLeavesBlock {

    @Inject(method = "randomTick", at = @At("RETURN"))
    public void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if(random.nextInt(20) == 1){
            FallingLeafEntity entity = new FallingLeafEntity(ModEntities.FALLING_LEAF, world);
            entity.setPos(pos.getX()+0.5, pos.getY()-0.25, pos.getZ()+0.5);
            world.addFreshEntity(entity);
        }
    }

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    public void onIsRandomlyTicking(CallbackInfoReturnable ci){
        ci.setReturnValue(true);
    }
}
