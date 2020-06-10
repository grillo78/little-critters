package com.grillo78.smallbugs.events;

import com.grillo78.smallbugs.Reference;
import com.grillo78.smallbugs.entity.FlyEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void registerEntityType(final RegistryEvent.Register<EntityType<?>> event) {
        EntityType<FlyEntity> type = EntityType.Builder.<FlyEntity>create(FlyEntity::new, EntityClassification.MISC)
                .size(0.25F, 0.25F).build(Reference.MODID + ":fly");
        type.setRegistryName(Reference.MODID, "fly");
        event.getRegistry().register(type);
    }

}
