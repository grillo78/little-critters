package com.grillo78.littlecritters.common.tileentities;

import com.grillo78.littlecritters.common.entities.AntEntity;
import com.grillo78.littlecritters.common.entities.ModEntities;
import com.grillo78.littlecritters.common.entities.ants.AntTypes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class AntHouseTileEntity extends TileEntity implements ITickableTileEntity {

    private int workersAmount = 1;
    private int fightersAmount = 0;
    private boolean hasQueen = true;
    private boolean hasPrincess = false;
    private boolean hasMale = false;

    private Random random = new Random();

    public AntHouseTileEntity() {
        super(ModTileEntities.ANT_HOUSE);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();

        compoundNBT.putBoolean("hasQueen", hasQueen);
        compoundNBT.putBoolean("hasPrincess", hasPrincess);
        compoundNBT.putBoolean("hasMale", hasMale);
        compoundNBT.putInt("workersAmount", workersAmount);
        compoundNBT.putInt("fightersAmount", fightersAmount);

        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        workersAmount = nbt.getInt("workersAmount");
        fightersAmount = nbt.getInt("fightersAmount");
        hasQueen = nbt.getBoolean("hasQueen");
        hasPrincess = nbt.getBoolean("hasPrincess");
        hasMale = nbt.getBoolean("hasMale");
    }

    @Override
    public void tick() {
        if(!level.isClientSide && workersAmount > 0 && random.nextInt(50) == 1){
            BlockPos pos = this.getBlockPos();
            AntEntity entity = new AntEntity(ModEntities.ANT_ENTITY, level, AntTypes.WORKER, pos);
            entity.setPos(pos.getX() + 0.5,pos.getY() + 0.25,pos.getZ() + 0.5);
            level.addFreshEntity(entity);
            workersAmount--;
        }

    }
}
