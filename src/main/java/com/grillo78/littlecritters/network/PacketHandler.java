package com.grillo78.littlecritters.network;

import com.grillo78.littlecritters.LittleCritters;
import com.grillo78.littlecritters.network.messages.IMessage;
import com.grillo78.littlecritters.network.messages.MessageCatchEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";

    public static SimpleChannel instance;
    private static int nextId = 0;

    /**
     * create the network channel and register the packets
     */
    public static void init()
    {
        // Create the Network channel
        instance = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(LittleCritters.MOD_ID, "network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();

        // Register packets
        register(MessageCatchEntity.class, new MessageCatchEntity());
    }

    /**
     * Method to register a packet
     * @param clazz Class of the packet
     * @param message Message object
     * @param <T>
     */
    private static <T> void register(Class<T> clazz, IMessage<T> message)
    {
        instance.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }
}