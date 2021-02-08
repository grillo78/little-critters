package com.grillo78.littlecritters.common.items;

import com.grillo78.littlecritters.network.PacketHandler;
import com.grillo78.littlecritters.network.messages.MessageCatchEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BugNetItem extends Item {

    private ArrayList<EntityType> catchable;

    public BugNetItem(Properties properties, ArrayList<EntityType> catchable) {
        super(properties);
        this.catchable = catchable;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.isRemote) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            RayTraceResult result = Minecraft.getInstance().objectMouseOver;
            if (result instanceof EntityRayTraceResult) {
                if (catchable.contains(((EntityRayTraceResult)result).getEntity().getType())){
                    PacketHandler.instance.sendToServer(new MessageCatchEntity(((EntityRayTraceResult)result).getEntity().getEntityId()));
                    return ActionResult.resultSuccess(itemstack);
                }
            }

        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
