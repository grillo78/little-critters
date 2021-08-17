package com.grillo78.littlecritters.common.blocks;

import com.grillo78.littlecritters.LittleCritters;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LittleCritters.MOD_ID);

    public static final Block ANT_HOUSE = register("ant_house", new AntHouseBlock(AbstractBlock.Properties.of(Material.DIRT)));

    private static <T extends Block> T register(String name, T item) {
        BLOCKS.register(name, () -> item);
        return item;
    }

}
