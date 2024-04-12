package com.grillo78.littlecritters.common.tileentities;

import com.grillo78.littlecritters.common.entities.AntEntity;
import com.grillo78.littlecritters.common.entities.ModEntities;
import com.grillo78.littlecritters.common.entities.ants.AntTypes;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class AntHouseTileEntity extends TileEntity implements ITickableTileEntity {

    private int workersInside = 1;
    private int workersOutside = 0;
    private int fightersAmount = 0;
    private boolean hasQueen = true;
    private boolean hasPrincess = false;
    private boolean hasMale = false;
    private int foodAmount = 0;

    private Random random = new Random();

    public AntHouseTileEntity() {
        super(ModTileEntities.ANT_HOUSE);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        readNetwork(p_230337_2_);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        writeNetwork(compound);
        return super.save(compound);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 1, this.writeNetwork(new CompoundNBT()));
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return writeNetwork(super.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.readNetwork(pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.deserializeCaps(tag);
        this.readNetwork(tag);
    }

    public CompoundNBT writeNetwork(CompoundNBT compoundNBT) {

        compoundNBT.putBoolean("hasQueen", hasQueen);
        compoundNBT.putBoolean("hasPrincess", hasPrincess);
        compoundNBT.putBoolean("hasMale", hasMale);
        compoundNBT.putInt("workersInside", workersInside);
        compoundNBT.putInt("fightersAmount", fightersAmount);
        compoundNBT.putInt("foodAmount", foodAmount);

        return compoundNBT;
    }

    private void readNetwork(CompoundNBT nbt){
        workersInside = nbt.getInt("workersInside");
        fightersAmount = nbt.getInt("fightersAmount");
        foodAmount = nbt.getInt("foodAmount");
        hasQueen = nbt.getBoolean("hasQueen");
        hasPrincess = nbt.getBoolean("hasPrincess");
        hasMale = nbt.getBoolean("hasMale");
    }

    @Override
    public void tick() {
        if(!level.isClientSide){
            if(workersInside > 0 && random.nextInt(50) == 1) {
                BlockPos pos = this.getBlockPos();
                AntEntity entity = new AntEntity(ModEntities.ANT_ENTITY, level, AntTypes.WORKER, pos);
                entity.setPos(pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5);
                level.addFreshEntity(entity);
                workersInside--;
                workersOutside++;
            }
            if (foodAmount>0){
                if(hasQueen && !hasPrincess){
                    hasPrincess = random.nextInt(500) == 1;
                }
                if(hasQueen && !hasMale){
                    hasMale = random.nextInt(500) == 1;
                }
                if(hasQueen && random.nextInt(500) == 1 && workersInside + workersOutside < 50){
                    addWorker();
                }
            }
        }
    }

    public void addFood(){
        foodAmount+=10;
    }

    public void addWorker(){
        workersInside++;
    }

    public void removeWorkerOutside(){
        workersOutside--;
    }
}
