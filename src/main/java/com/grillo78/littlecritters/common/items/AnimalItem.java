package com.grillo78.littlecritters.common.items;

import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import com.grillo78.littlecritters.common.entities.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AnimalItem extends Item {

    public AnimalItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        ItemStack stack = context.getItem();
        if (!world.isRemote) {
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
            entity.setPositionAndRotation(context.getPos().up().getX() + 0.5, context.getPos().up().getY(), context.getPos().up().getZ() + 0.5, 0, 0);
            world.addEntity(entity);
            return ActionResultType.CONSUME;
        }
        return ActionResultType.SUCCESS;
    }
}
