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

    @Override
    public void onInitializeClient() {

        toggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.potatofps.toggle",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_O,
                        KeyBinding.Category.MISC
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (toggleKey.wasPressed()) {
                PotatoConfig.potatoMode = !PotatoConfig.potatoMode;

                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal("Potato Mode: " + (PotatoConfig.potatoMode ? "ON" : "OFF")),
                            true
                    );
                }
            }

            if (!PotatoConfig.potatoMode || client.player == null) return;

            if (PotatoConfig.disableClouds) {
                client.options.getCloudRenderMode().setValue(CloudRenderMode.OFF);
            }

            if (PotatoConfig.disableShadows) {
                client.options.getEntityShadows().setValue(false);
            }

            int fps = MinecraftClient.getInstance().getCurrentFps();

            int render = client.options.getViewDistance().getValue();
            int sim = client.options.getSimulationDistance().getValue();

            if (fps < PotatoConfig.targetFps && render > PotatoConfig.minRender) {

                client.options.getViewDistance().setValue(render - 1);
                client.options.getSimulationDistance().setValue(
                        Math.max(PotatoConfig.minRender, sim - 1)
                );
                client.options.getEntityDistanceScaling().setValue(0.5);

                System.out.println("PotatoFPS lowering render distance → " + (render - 1));
            }

            if (fps > PotatoConfig.targetFps + 25 && render < PotatoConfig.maxRender) {

                client.options.getViewDistance().setValue(render + 1);
                client.options.getSimulationDistance().setValue(
                        Math.min(PotatoConfig.maxRender, sim + 1)
                );
                client.options.getEntityDistanceScaling().setValue(1.0);

                System.out.println("PotatoFPS increasing render distance → " + (render + 1));
            }

        });

        // HUD renderer (separate from tick event)
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {

            MinecraftClient client = MinecraftClient.getInstance();

            if (!PotatoConfig.potatoMode || !PotatoConfig.showHud || client.player == null) return;

            int fps = client.getCurrentFps();
            int render = client.options.getViewDistance().getValue();

            String text = "Potato Mode | FPS: " + fps + " | Target: " + PotatoConfig.targetFps + " | Render: " + render;

            drawContext.drawText(
                    client.textRenderer,
                    text,
                    5,
                    5,
                    0xFFFFFF,
                    true
            );
        });
    }
}
