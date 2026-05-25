package com.example.potatofps;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PotatoConfigScreen {

    public static Screen create(Screen parent) {

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Potato FPS Settings"))
                .setSavingRunnable(PotatoConfigManager::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(
                Component.literal("Performance")
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(
                                Component.literal("Enable Potato Mode"),
                                PotatoConfig.potatoMode
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(value ->
                                PotatoConfig.potatoMode = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(
                                Component.literal("Disable Entity Shadows"),
                                PotatoConfig.disableShadows
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(value ->
                                PotatoConfig.disableShadows = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(
                                Component.literal("Disable Clouds"),
                                PotatoConfig.disableClouds
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(value ->
                                PotatoConfig.disableClouds = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(
                                Component.literal("Disable Vignette"),
                                PotatoConfig.disableVignette
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(value ->
                                PotatoConfig.disableVignette = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(
                                Component.literal("Ultra Particle Reduction"),
                                PotatoConfig.ultraParticleReduction
                        )
                        .setDefaultValue(false)
                        .setSaveConsumer(value ->
                                PotatoConfig.ultraParticleReduction = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(
                                Component.literal("Reduce Entity Render Distance"),
                                PotatoConfig.reduceEntityDistance
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(value ->
                                PotatoConfig.reduceEntityDistance = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(
                                Component.literal("Disable Rain Splash Particles"),
                                PotatoConfig.disableRainParticles
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(value ->
                                PotatoConfig.disableRainParticles = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startIntSlider(
                                Component.literal("Minimum Render Distance"),
                                PotatoConfig.minRender,
                                2,
                                32
                        )
                        .setDefaultValue(8)
                        .setSaveConsumer(value ->
                                PotatoConfig.minRender = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startIntSlider(
                                Component.literal("Maximum Render Distance"),
                                PotatoConfig.maxRender,
                                4,
                                32
                        )
                        .setDefaultValue(16)
                        .setSaveConsumer(value ->
                                PotatoConfig.maxRender = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startIntSlider(
                                Component.literal("Target FPS"),
                                PotatoConfig.targetFps,
                                30,
                                240
                        )
                        .setDefaultValue(70)
                        .setSaveConsumer(value ->
                                PotatoConfig.targetFps = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(
                                Component.literal("Show Potato HUD"),
                                PotatoConfig.showHud
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(value ->
                                PotatoConfig.showHud = value)
                        .build()
        );

        general.addEntry(
                entryBuilder.startEnumSelector(
                                Component.literal("Adjustment Speed"),
                                AdjustmentSpeed.class,
                                AdjustmentSpeed.values()[PotatoConfig.adjustmentSpeed]
                        )
                        .setDefaultValue(AdjustmentSpeed.SLOW)
                        .setSaveConsumer(value ->
                                PotatoConfig.adjustmentSpeed = value.ordinal())
                        .build()
        );

        general.addEntry(
                entryBuilder.startIntSlider(
                                Component.literal("Entity Render Distance"),
                                PotatoConfig.entityRenderDistance,
                                16,
                                128
                        )
                        .setDefaultValue(64)
                        .setSaveConsumer(value -> {
                            PotatoConfig.entityRenderDistance = value;
                            PotatoConfigManager.save();
                        })
                        .build()
        );

        return builder.build();
    }
}
