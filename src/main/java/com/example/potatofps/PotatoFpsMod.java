package com.example.potatofps;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class PotatoFpsMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Potato FPS Mod Loaded!");
        MinecraftClient client = MinecraftClient.getInstance();
    }
}
