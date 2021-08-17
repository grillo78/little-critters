package com.grillo78.littlecritters.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import java.util.stream.Stream;

public class AntHouseBlock extends Block {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(11, 0, 3, 13, 1, 4),
            Block.box(4, 0, 2, 11, 1, 3),
            Block.box(3, 0, 3, 4, 1, 5),
            Block.box(2, 0, 5, 3, 1, 11),
            Block.box(3, 0, 11, 4, 1, 12),
            Block.box(4, 0, 11, 5, 1, 13),
            Block.box(5, 0, 12, 7, 1, 13),
            Block.box(6, 0, 13, 12, 1, 14),
            Block.box(11, 0, 12, 13, 1, 13),
            Block.box(12, 0, 10, 13, 1, 12),
            Block.box(13, 0, 5, 14, 1, 11),
            Block.box(12, 0, 4, 13, 1, 5),
            Block.box(4, 1, 3, 5, 2, 5),
            Block.box(3, 1, 5, 4, 2, 11),
            Block.box(4, 1, 10, 5, 2, 11),
            Block.box(5, 1, 10, 6, 2, 12),
            Block.box(6, 1, 11, 8, 2, 12),
            Block.box(7, 1, 12, 11, 2, 13),
            Block.box(10, 1, 11, 12, 2, 12),
            Block.box(11, 1, 9, 12, 2, 11),
            Block.box(12, 1, 5, 13, 2, 10),
            Block.box(11, 1, 4, 12, 2, 5),
            Block.box(5, 1, 3, 11, 2, 4),
            Block.box(5, 2, 4, 11, 3, 5),
            Block.box(4, 2, 5, 5, 3, 10),
            Block.box(5, 2, 9, 6, 3, 10),
            Block.box(6, 2, 9, 7, 3, 11),
            Block.box(7, 2, 10, 9, 3, 11),
            Block.box(8, 2, 11, 10, 3, 12),
            Block.box(10, 2, 9, 11, 3, 11),
            Block.box(11, 2, 5, 12, 3, 9),
            Block.box(5, 3, 5, 11, 4, 6),
            Block.box(9, 3, 6, 11, 4, 9),
            Block.box(9, 3, 9, 10, 4, 11),
            Block.box(7, 3, 7, 9, 4, 10),
            Block.box(5, 3, 7, 7, 4, 9),
            Block.box(5, 3, 6, 8, 4, 7)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public AntHouseBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        return !p_196271_1_.canSurvive(p_196271_4_, p_196271_5_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        BlockPos blockpos = p_196260_3_.below();;
        return this.mayPlaceOn(p_196260_2_.getBlockState(blockpos), p_196260_2_, blockpos);
    }

    protected boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return p_200014_1_.is(Blocks.GRASS_BLOCK) || p_200014_1_.is(Blocks.DIRT) || p_200014_1_.is(Blocks.COARSE_DIRT) || p_200014_1_.is(Blocks.PODZOL) || p_200014_1_.is(Blocks.FARMLAND);
    }
}
