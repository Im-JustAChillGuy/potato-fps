package com.example.potatofps;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class PotatoConfigScreen {

    public static Screen create(Screen parent) {

       ConfigBuilder builder = ConfigBuilder.create()
        .setParentScreen(parent)
        .setTitle(Text.literal("Potato FPS Settings"))
        .setSavingRunnable(() -> PotatoConfigManager.save());
        
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Text.literal("Performance"));

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Enable Potato Mode"), PotatoConfig.potatoMode)
                        .setDefaultValue(true)
                        .setSaveConsumer(newValue -> PotatoConfig.potatoMode = newValue)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Disable Entity Shadows"), PotatoConfig.disableShadows)
                        .setDefaultValue(true)
                        .setSaveConsumer(newValue -> PotatoConfig.disableShadows = newValue)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Disable Clouds"), PotatoConfig.disableClouds)
                        .setDefaultValue(true)
                        .setSaveConsumer(newValue -> PotatoConfig.disableClouds = newValue)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Disable Vignette"), PotatoConfig.disableVignette)
                        .setDefaultValue(true)
                        .setSaveConsumer(newValue -> PotatoConfig.disableVignette = newValue)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Ultra Particle Reduction"), PotatoConfig.ultraParticleReduction)
                        .setDefaultValue(false)
                        .setSaveConsumer(newValue -> PotatoConfig.ultraParticleReduction = newValue)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Reduce Entity Render Distance"), PotatoConfig.reduceEntityDistance)
                        .setDefaultValue(true)
                        .setSaveConsumer(newValue -> PotatoConfig.reduceEntityDistance = newValue)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Disable Rain Splash Particles"), PotatoConfig.disableRainParticles)
                        .setDefaultValue(true)
                        .setSaveConsumer(newValue -> PotatoConfig.disableRainParticles = newValue)
                        .build()
        );

        general.addEntry(
                entryBuilder.startIntSlider(
                        Text.literal("Minimum Render Distance"),
                        PotatoConfig.minRender,
                        2,
                        32
                )
                        .setDefaultValue(8)
                        .setSaveConsumer(newValue -> PotatoConfig.minRender = newValue)
                        .build()
        );

        general.addEntry(
                entryBuilder.startIntSlider(
                        Text.literal("Maximum Render Distance"),
                        PotatoConfig.maxRender,
                        4,
                        32
                )
                        .setDefaultValue(16)
                        .setSaveConsumer(newValue -> PotatoConfig.maxRender = newValue)
                        .build()
        );
        general.addEntry(
    entryBuilder.startIntSlider(
            Text.literal("Target FPS"),
            PotatoConfig.targetFps,
            30,
            240
    )
    .setDefaultValue(70)
    .setSaveConsumer(newValue -> PotatoConfig.targetFps = newValue)
    .build()
);
        general.addEntry(
    entryBuilder.startBooleanToggle(Text.literal("Show Potato HUD"), PotatoConfig.showHud)
        .setDefaultValue(true)
        .setSaveConsumer(newValue -> PotatoConfig.showHud = newValue)
        .build()
);
        general.addEntry(
    entryBuilder.startEnumSelector(
            Text.literal("Adjustment Speed"),
            AdjustmentSpeed.class,
            AdjustmentSpeed.values()[PotatoConfig.adjustmentSpeed]
    )
    .setDefaultValue(AdjustmentSpeed.SLOW)
    .setSaveConsumer(newValue -> PotatoConfig.adjustmentSpeed = newValue.ordinal())
    .build()
);
        builder.startIntSlider(
        Text.literal("Entity Render Distance"),
        PotatoConfig.entityRenderDistance,
        16,
        128
)
.setSaveConsumer(value -> config.entityRenderDistance = value)
.build(
    
);

        return builder.build();
    }
}
