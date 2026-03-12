package com.example.potatofps.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;

import com.example.potatofps.PotatoConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRenderMixin<T extends Entity> {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void potatoFPS$cullEntities(T entity, CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        if (PotatoConfig.skipInvisible && entity.isInvisible()) {
            ci.cancel();
            return;
        }

        double max = PotatoConfig.entityRenderDistance * PotatoConfig.entityRenderDistance;

        if (entity.squaredDistanceTo(client.player) > max) {
            ci.cancel();
        }
    }
}
