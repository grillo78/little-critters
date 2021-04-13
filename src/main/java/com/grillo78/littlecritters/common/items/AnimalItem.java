package com.grillo78.littlecritters.common.items;

import com.grillo78.littlecritters.common.entities.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class AnimalItem extends Item {

    public AnimalItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        if (!world.isClientSide) {
            Entity entity;
            switch (stack.getTag().getString("animal")) {
                case "firefly":
                    entity = ModEntities.FIRE_FLY_ENTITY.create(world);
                    break;
                case "silverfish":
                    entity = EntityType.SILVERFISH.create(world);
                    break;
                case "bee":
                    entity = EntityType.BEE.create(world);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + stack.getTag().getString("animal"));
            }
            entity.absMoveTo(context.getClickedPos().above().getX() + 0.5, context.getClickedPos().above().getY(), context.getClickedPos().above().getZ() + 0.5, 0, 0);
            world.addFreshEntity(entity);
            return ActionResultType.CONSUME;
        }
        return ActionResultType.SUCCESS;
    }
}
