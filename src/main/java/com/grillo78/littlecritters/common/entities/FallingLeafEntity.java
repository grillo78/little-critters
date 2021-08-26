package com.grillo78.littlecritters.common.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class FallingLeafEntity extends Entity {

    private float xRot = random.nextInt(360);
    private float yRot = random.nextInt(360);
    private float zRot = random.nextInt(360);
    private int despawnCount = 2400;

    public FallingLeafEntity(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {

    }

    public Vector3f getLeafRotation() {
        return new Vector3f(xRot, yRot, zRot);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {

    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            if (!this.onGround) {
                xRot += 3.5F;
                yRot += 1;
                zRot += 1.25F;
            }
        } else {
            if (despawnCount>0 && getVehicle() == null)
                despawnCount--;
            else
                remove();
        }
        this.onGround = getDeltaMovement().y == 0;
        move(MoverType.SELF, getDeltaMovement().add(0,-0.1,0));
        if (!this.onGround) {
            setDeltaMovement(new Vector3d(0.1, 0, 0.1));
        } else {
            setDeltaMovement(Vector3d.ZERO);
        }
    }

    public boolean isPickable() {
        return !this.removed;
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 4600;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }
}
