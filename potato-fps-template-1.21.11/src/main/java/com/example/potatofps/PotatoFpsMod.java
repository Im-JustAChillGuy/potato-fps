package com.example.potatofps;

import com.mojang.blaze3d.platform.InputConstants;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

import org.lwjgl.glfw.GLFW;

public class PotatoFpsMod implements ClientModInitializer {

    public static float particleMultiplier = 1.0f;

    private KeyMapping toggleKey;
    private KeyMapping hudToggleKey;

    @Override
    public void onInitializeClient() {

        PotatoConfigManager.load();

        // Keybinds
        toggleKey = new KeyMapping(
                "key.potatofps.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "key.categories.misc"
        );

        hudToggleKey = new KeyMapping(
                "key.potatofps.togglehud",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "key.categories.misc"
        );

        // Register keybinds manually
        Options options = Minecraft.getInstance().options;

        options.keyMappings =
                java.util.Arrays.copyOf(
                        options.keyMappings,
                        options.keyMappings.length + 2
                );

        options.keyMappings[options.keyMappings.length - 2] = toggleKey;
        options.keyMappings[options.keyMappings.length - 1] = hudToggleKey;

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

            // Toggle Potato Mode
            while (toggleKey.consumeClick()) {

                PotatoConfig.potatoMode = !PotatoConfig.potatoMode;

                client.player.sendSystemMessage(
                        Component.literal(
                                "Potato Mode: "
                                        + (PotatoConfig.potatoMode ? "ON" : "OFF")
                        )
                );
            }

            // Toggle HUD
            while (hudToggleKey.consumeClick()) {

                PotatoConfig.showHud = !PotatoConfig.showHud;

                client.player.sendSystemMessage(
                        Component.literal(
                                "Potato HUD: "
                                        + (PotatoConfig.showHud ? "ON" : "OFF")
                        )
                );
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

        // HUD temporarily disabled for 26.1 port
    }
}
