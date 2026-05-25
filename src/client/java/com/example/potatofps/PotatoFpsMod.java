package com.example.potatofps;

import com.mojang.blaze3d.platform.InputConstants;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
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
        toggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.potatofps.toggle",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_O,
                        "category.potatofps"
                )
        );

        hudToggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.potatofps.togglehud",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_H,
                        "category.potatofps"
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) return;

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

                client.player.displayClientMessage(
                        Component.literal(
                                "Potato Mode: " +
                                (PotatoConfig.potatoMode ? "ON" : "OFF")
                        ),
                        true
                );
            }

            // Toggle HUD
            while (hudToggleKey.consumeClick()) {

                PotatoConfig.showHud = !PotatoConfig.showHud;

                client.player.displayClientMessage(
                        Component.literal(
                                "Potato HUD: " +
                                (PotatoConfig.showHud ? "ON" : "OFF")
                        ),
                        true
                );
            }

            if (!PotatoConfig.potatoMode) return;

            int render = client.options.getEffectiveRenderDistance();

            // Lower render distance
            if (fps < PotatoConfig.targetFps && render > PotatoConfig.minRender) {

                System.out.println(
                        "PotatoFPS lowering render distance → " + (render - 1)
                );
            }

            // Increase render distance
            if (fps > PotatoConfig.targetFps + 25 && render < PotatoConfig.maxRender) {

                System.out.println(
                        "PotatoFPS increasing render distance → " + (render + 1)
                );
            }
        });

        // HUD Renderer
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {

            Minecraft client = Minecraft.getInstance();

            if (!PotatoConfig.potatoMode
                    || !PotatoConfig.showHud
                    || client.player == null) {
                return;
            }

            int fps = client.getFps();

            int color;

            if (fps < PotatoConfig.targetFps - 15) {
                color = 0xFF5555;
            } else if (fps < PotatoConfig.targetFps) {
                color = 0xFFFF55;
            } else {
                color = 0x55FF55;
            }

            drawContext.drawString(
                    client.font,
                    "🥔 Potato Mode | FPS: " + fps,
                    5,
                    5,
                    color
            );
        });
    }
}
