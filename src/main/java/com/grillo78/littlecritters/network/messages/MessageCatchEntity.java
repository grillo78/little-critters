package com.grillo78.littlecritters.network.messages;

import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import com.grillo78.littlecritters.common.entities.ModEntities;
import com.grillo78.littlecritters.common.items.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageCatchEntity implements IMessage<MessageCatchEntity>{

    private int entityID;

    public MessageCatchEntity() {
    }

    public MessageCatchEntity(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void encode(MessageCatchEntity message, PacketBuffer buffer) {
        buffer.writeInt(message.entityID);
    }

    @Override
    public MessageCatchEntity decode(PacketBuffer buffer) {
        return new MessageCatchEntity(buffer.readInt());
    }

    @Override
    public void handle(MessageCatchEntity message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->{
            PlayerEntity playerEntity = supplier.get().getSender();
            World world = supplier.get().getSender().world;
            LivingEntity entity = (LivingEntity) world.getEntityByID(message.entityID);
            ItemStack animalStack = new ItemStack(ModItems.ANIMAL);
            animalStack.setTag(new CompoundNBT());
            if(entity.getType() == ModEntities.FIRE_FLY_ENTITY){
                animalStack.getTag().putString("animal","firefly");
            }
            if(entity.getType() == EntityType.BEE){
                animalStack.getTag().putString("animal","bee");
            }
            if(entity.getType() == EntityType.SILVERFISH){
                animalStack.getTag().putString("animal","silverfish");
            }
            playerEntity.dropItem(animalStack,false);
            entity.remove();
        });
        supplier.get().setPacketHandled(true);
    }
}
