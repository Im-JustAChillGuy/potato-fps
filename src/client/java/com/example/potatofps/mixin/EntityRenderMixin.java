package com.example.potatofps.mixin;

import com.example.potatofps.PotatoConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.renderer.entity.EntityRenderer")
public class EntityRenderMixin<T extends Entity> {

    @Inject(
            method = "shouldRender",
            at = @At("HEAD"),
            cancellable = true
    )
    private void potatoFPS$cullEntities(
            T entity,
            CallbackInfoReturnable<Boolean> cir
    ) {

        Minecraft client = Minecraft.getInstance();

        if (client.player == null) {
            return;
        }

        if (PotatoConfig.skipInvisible && entity.isInvisible()) {
            cir.setReturnValue(false);
            return;
        }

        double max =
                PotatoConfig.entityRenderDistance
                * PotatoConfig.entityRenderDistance;

        if (entity.distanceToSqr(client.player) > max) {
            cir.setReturnValue(false);
        }
    }
}
