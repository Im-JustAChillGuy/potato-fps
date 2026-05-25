package com.example.potatofps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class PotatoFpsMod implements ClientModInitializer {

    public static float particleMultiplier = 1.0f;

    @Override
    public void onInitializeClient() {

        PotatoConfigManager.load();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) {
                return;
            }

            int fps = client.getFps();

            // Dynamic particle control
            if (fps < PotatoConfig.targetFps - 20) {
                particleMultiplier = 0.2f;
            } else if (fps < PotatoConfig.targetFps) {
                particleMultiplier = 0.5f;
            } else {
                particleMultiplier = 1.0f;
            }

            if (!PotatoConfig.potatoMode) {
                return;
            }

            int render = client.options.getEffectiveRenderDistance();

            // Lower render distance
            if (fps < PotatoConfig.targetFps
                    && render > PotatoConfig.minRender) {

                System.out.println(
                        "PotatoFPS lowering render distance → "
                                + (render - 1)
                );
            }

            // Increase render distance
            if (fps > PotatoConfig.targetFps + 25
                    && render < PotatoConfig.maxRender) {

                System.out.println(
                        "PotatoFPS increasing render distance → "
                                + (render + 1)
                );
            }
        });
    }
}
