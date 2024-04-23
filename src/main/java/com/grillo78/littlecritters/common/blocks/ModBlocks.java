package com.grillo78.littlecritters.common.blocks;

import com.grillo78.littlecritters.ItemGroups;
import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.common.items.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LittleCritters.MOD_ID);

    public static final Block ANT_HOUSE = register("ant_house", new AntHouseBlock(AbstractBlock.Properties.of(Material.DIRT)));
    public static final Block CHICKEN_NEST = registerWithItem("chicken_nest", new ChickenNestBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission()));

    private static <T extends Block> T register(String name, T item) {
        BLOCKS.register(name, () -> item);
        return item;
    }
    private static <T extends Block> T registerWithItem(String name, T block) {
        BLOCKS.register(name, () -> block);
        ModItems.ITEMS.register(name, ()->new BlockItem(block, new Item.Properties().tab(ItemGroups.INSTANCE)));
        return block;
    }

}
