package com.example.potatofps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class PotatoFpsMod implements ClientModInitializer {

    private static final int TARGET_FPS = 70;

    private boolean enabled = true;

    private int minRender = 4;
    private int maxRender = 16;

    private KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {

        toggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.potatofps.toggle",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_O,
                        "category.potatofps"
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (toggleKey.wasPressed()) {
                enabled = !enabled;

                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal("Potato Mode: " + (enabled ? "ON" : "OFF")),
                            true
                    );
                }
            }

            if (!enabled || client.player == null) return;

            int fps = MinecraftClient.getInstance().getCurrentFps();

            int render = client.options.getViewDistance().getValue();
            int sim = client.options.getSimulationDistance().getValue();

            if (fps < TARGET_FPS && render > minRender) {

                client.options.getViewDistance().setValue(render - 1);
                client.options.getSimulationDistance().setValue(Math.max(minRender, sim - 1));
                client.options.getEntityDistanceScaling().setValue(0.5);
                client.options.getParticles().setValue(ParticlesMode.MINIMAL);

                System.out.println("PotatoFPS lowering render distance → " + (render - 1));
            }

            if (fps > TARGET_FPS + 25 && render < maxRender) {

                client.options.getViewDistance().setValue(render + 1);
                client.options.getSimulationDistance().setValue(Math.min(maxRender, sim + 1));
                client.options.getEntityDistanceScaling().setValue(1.0);
                client.options.getParticles().setValue(ParticlesMode.ALL);

                System.out.println("PotatoFPS increasing render distance → " + (render + 1));
            }
        });
    }
}
