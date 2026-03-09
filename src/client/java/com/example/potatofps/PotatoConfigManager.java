package com.example.potatofps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class PotatoConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final File CONFIG_FILE =
            new File(FabricLoader.getInstance().getConfigDir().toFile(), "potatofps.json");

    public static void load() {
        try {
            if (CONFIG_FILE.exists()) {
                FileReader reader = new FileReader(CONFIG_FILE);
                PotatoConfig loaded = GSON.fromJson(reader, PotatoConfig.class);
                reader.close();

                PotatoConfig.potatoMode = loaded.potatoMode;
                PotatoConfig.disableClouds = loaded.disableClouds;
                PotatoConfig.disableShadows = loaded.disableShadows;
                PotatoConfig.minRender = loaded.minRender;
                PotatoConfig.maxRender = loaded.maxRender;
                PotatoConfig.targetFps = loaded.targetFps;
                PotatoConfig.showHud = loaded.showHud;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            FileWriter writer = new FileWriter(CONFIG_FILE);
            public static void save() {
    try {
        FileWriter writer = new FileWriter(CONFIG_FILE);

        JsonObject json = new JsonObject();

        json.addProperty("potatoMode", PotatoConfig.potatoMode);
        json.addProperty("disableClouds", PotatoConfig.disableClouds);
        json.addProperty("disableShadows", PotatoConfig.disableShadows);
        json.addProperty("minRender", PotatoConfig.minRender);
        json.addProperty("maxRender", PotatoConfig.maxRender);
        json.addProperty("targetFps", PotatoConfig.targetFps);
        json.addProperty("adjustmentSpeed", PotatoConfig.adjustmentSpeed);
        json.addProperty("showHud", PotatoConfig.showHud);

        GSON.toJson(json, writer);

        writer.close();

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
