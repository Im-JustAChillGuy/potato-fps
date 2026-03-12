package com.example.potatofps.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import com.example.potatofps.PotatoConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private <T extends Entity> void potatoFPS$cancelEntities(
            T entity,
            double x,
            double y,
            double z,
            float yaw,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            CallbackInfo ci) {

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
