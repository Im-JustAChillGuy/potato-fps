package com.example.potatofps.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;

import com.example.potatofps.PotatoConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class FrustumCullingMixin<T extends Entity> {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void potatoFPS$frustumCull(
            T entity,
            Frustum frustum,
            double x,
            double y,
            double z,
            CallbackInfoReturnable<Boolean> cir) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;

        // Skip invisible entities
        if (PotatoConfig.skipInvisible && entity.isInvisible()) {
            cir.setReturnValue(false);
            return;
        }

        // Distance culling
        double max = PotatoConfig.entityRenderDistance * PotatoConfig.entityRenderDistance;

        if (entity.squaredDistanceTo(client.player) > max) {
            cir.setReturnValue(false);
        }
    }
}
