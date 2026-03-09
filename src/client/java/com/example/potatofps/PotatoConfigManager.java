package com.example.potatofps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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

            if (!CONFIG_FILE.exists()) {
                save();
                return;
            }

            FileReader reader = new FileReader(CONFIG_FILE);
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            reader.close();

            if (json == null) return;

            PotatoConfig.potatoMode = json.get("potatoMode").getAsBoolean();
            PotatoConfig.disableClouds = json.get("disableClouds").getAsBoolean();
            PotatoConfig.disableShadows = json.get("disableShadows").getAsBoolean();
            PotatoConfig.minRender = json.get("minRender").getAsInt();
            PotatoConfig.maxRender = json.get("maxRender").getAsInt();
            PotatoConfig.targetFps = json.get("targetFps").getAsInt();
            PotatoConfig.adjustmentSpeed = json.get("adjustmentSpeed").getAsInt();
            PotatoConfig.showHud = json.get("showHud").getAsBoolean();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
