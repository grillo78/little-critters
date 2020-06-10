package com.grillo78.smallbugs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin {

	@Inject(method = "register(Ljava/lang/String;Lnet/minecraft/entity/EntityType$Builder;)Lnet/minecraft/entity/EntityType;", at = @At("HEAD"))
	private static <T extends Entity> void onRegister(String key, EntityType.Builder<T> builder, CallbackInfoReturnable<EntityType> ci)  {
		switch (key) {
			case "spider":
			case "cave_spider":
			case "silverfish":
			case "bee":
				builder.size(0.25f, 0.25f);
				break;
		}
	}
}
