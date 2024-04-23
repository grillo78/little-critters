package com.grillo78.littlecritters;


import com.grillo78.littlecritters.common.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {

    public static final ItemGroup INSTANCE = new ItemGroup(LittleCritters.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BUG_NET);
        }
    };
}
