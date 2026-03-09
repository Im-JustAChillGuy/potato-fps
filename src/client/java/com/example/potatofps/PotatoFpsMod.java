package com.example.potatofps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import org.lwjgl.glfw.GLFW;

public class PotatoFpsMod implements ClientModInitializer {

    private KeyBinding toggleKey;
    private KeyBinding hudToggleKey;

    @Override
    public void onInitializeClient() {

        PotatoConfigManager.load();

        // Toggle Potato Mode key
        toggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.potatofps.toggle",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_O,
                        KeyBinding.Category.MISC
                )
        );

        // Toggle HUD key
        hudToggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.potatofps.togglehud",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_H,
                        KeyBinding.Category.MISC
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            // Toggle Potato Mode
            while (toggleKey.wasPressed()) {
                PotatoConfig.potatoMode = !PotatoConfig.potatoMode;

                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal("Potato Mode: " + (PotatoConfig.potatoMode ? "ON" : "OFF")),
                            true
                    );
                }
            }

            // Toggle HUD
            while (hudToggleKey.wasPressed()) {
                PotatoConfig.showHud = !PotatoConfig.showHud;

                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal("Potato HUD: " + (PotatoConfig.showHud ? "ON" : "OFF")),
                            true
                    );
                }
            }

            if (!PotatoConfig.potatoMode || client.player == null) return;

            // Apply optimizations
            if (PotatoConfig.disableClouds) {
                client.options.getCloudRenderMode().setValue(CloudRenderMode.OFF);
            }

            if (PotatoConfig.disableShadows) {
                client.options.getEntityShadows().setValue(false);
            }

            int fps = MinecraftClient.getInstance().getCurrentFps();

            int render = client.options.getViewDistance().getValue();
            int sim = client.options.getSimulationDistance().getValue();

            // Lower render distance if FPS drops
            if (fps < PotatoConfig.targetFps && render > PotatoConfig.minRender) {

                client.options.getViewDistance().setValue(render - 1);

                client.options.getSimulationDistance().setValue(
                        Math.max(PotatoConfig.minRender, sim - 1)
                );

                client.options.getEntityDistanceScaling().setValue(0.5);

                System.out.println("PotatoFPS lowering render distance → " + (render - 1));
            }

            // Raise render distance if FPS is good
            if (fps > PotatoConfig.targetFps + 25 && render < PotatoConfig.maxRender) {

                client.options.getViewDistance().setValue(render + 1);

                client.options.getSimulationDistance().setValue(
                        Math.min(PotatoConfig.maxRender, sim + 1)
                );

                client.options.getEntityDistanceScaling().setValue(1.0);

                System.out.println("PotatoFPS increasing render distance → " + (render + 1));
            }

        });

        // HUD Renderer
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {

            MinecraftClient client = MinecraftClient.getInstance();

            if (!PotatoConfig.potatoMode || !PotatoConfig.showHud || client.player == null) return;

            int fps = client.getCurrentFps();
            int render = client.options.getViewDistance().getValue();

            int color;

            if (fps < PotatoConfig.targetFps - 15) {
                color = 0xFF5555; // red
            } else if (fps < PotatoConfig.targetFps) {
                color = 0xFFFF55; // yellow
            } else {
                color = 0x55FF55; // green
            }

            drawContext.drawText(client.textRenderer, "🥔 Potato Mode | FPS: ", 5, 5, 0xFFFFFF, true);

            drawContext.drawText(client.textRenderer, String.valueOf(fps), 135, 5, color, true);

            drawContext.drawText(
                    client.textRenderer,
                    " | Target: " + PotatoConfig.targetFps + " | Render: " + render,
                    160,
                    5,
                    0xFFFFFF,
                    true
            );
        });
    }
}
