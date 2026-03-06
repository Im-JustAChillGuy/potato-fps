package com.example.potatofps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;

public class PotatoFpsMod implements ClientModInitializer {

    private static final int FPS_THRESHOLD = 65;

    private boolean optimised = false;

    private double originalEntityDistance;
    private int originalMipmaps;
    private int originalSimDistance;

    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) return;

            int fps = MinecraftClient.getInstance().getCurrentFps();

            if (fps < FPS_THRESHOLD && !optimised) {
                enableOptimisation(client);
                optimised = true;
            }

            if (fps > FPS_THRESHOLD + 25 && optimised) {
                disableOptimisation(client);
                optimised = false;
            }

        });
    }

    private void enableOptimisation(MinecraftClient client) {

        originalEntityDistance = client.options.getEntityDistanceScaling().getValue();
        originalMipmaps = client.options.getMipmapLevels().getValue();
        originalSimDistance = client.options.getSimulationDistance().getValue();

        client.options.getParticles().setValue(ParticlesMode.MINIMAL);
        client.options.getEntityDistanceScaling().setValue(0.4);
        client.options.getMipmapLevels().setValue(0);
        client.options.getSimulationDistance().setValue(4);
    }

    private void disableOptimisation(MinecraftClient client) {

        client.options.getEntityDistanceScaling().setValue(originalEntityDistance);
        client.options.getMipmapLevels().setValue(originalMipmaps);
        client.options.getSimulationDistance().setValue(originalSimDistance);

        client.options.getParticles().setValue(ParticlesMode.ALL);
    }
}
