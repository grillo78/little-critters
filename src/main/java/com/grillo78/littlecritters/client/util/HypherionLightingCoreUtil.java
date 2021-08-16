package com.grillo78.littlecritters.client.util;

import com.grillo78.littlecritters.common.entities.FireFlyEntity;
import net.hypherionmc.hypcore.api.APIUtils;
import net.hypherionmc.hypcore.api.ColoredLightManager;
import net.hypherionmc.hypcore.api.Light;
import net.minecraft.entity.Entity;

public class HypherionLightingCoreUtil {

    public static void registerLightProvider(Entity entity) {
        ColoredLightManager.registerProvider(entity, HypherionLightingCoreUtil::yourColoredLightEvent);
    }

    public static Light yourColoredLightEvent(Entity ent) {

        Light light = null;

        if(ent instanceof FireFlyEntity){
            light = Light.builder().pos(APIUtils.entityPos(ent)).color(0f, 1.0f, 0.1f, 1.0f).radius(1.5F).build();
        }


        return light;
    }
}
