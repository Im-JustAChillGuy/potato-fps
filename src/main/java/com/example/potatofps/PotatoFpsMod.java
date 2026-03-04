package com.example.potatofps;

import net.fabricmc.api.ClientModInitializer;

public class PotatoFpsMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Potato FPS mod loaded!");
    }
}
