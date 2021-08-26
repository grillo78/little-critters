package com.grillo78.littlecritters.common.tileentities;

import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.common.blocks.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, LittleCritters.MOD_ID);

    public static final TileEntityType<AntHouseTileEntity> ANT_HOUSE = register("ant_house", TileEntityType.Builder
            .of(AntHouseTileEntity::new, ModBlocks.ANT_HOUSE).build(null));

    private static <T extends TileEntityType> T register(String name, T type) {
        TILE_ENTITIES.register(name, () -> type);
        return type;
    }
}
