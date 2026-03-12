package com.example.potatofps.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRenderMixin<T extends Entity> {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void cancelFarEntities(T entity, double x, double y, double z, float yaw, float tickDelta, CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;

        double distance = entity.distanceTo(client.player);

        if (distance > 64) {
            ci.cancel(); // stop rendering the entity
        }
    }
}
