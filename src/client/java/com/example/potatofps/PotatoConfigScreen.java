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
                .setTitle(Text.literal("Potato FPS Settings"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Text.literal("Performance"));

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Enable Potato Mode"), true)
                        .setDefaultValue(true)
                        .build()
        );


        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Disable Entity Shadows"), true)
                        .setDefaultValue(true)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Disable Clouds"), true)
                        .setDefaultValue(true)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Disable Vignette"), true)
                        .setDefaultValue(true)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Ultra Particle Reduction"), false)
                        .setDefaultValue(false)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Reduce Entity Render Distance"), true)
                        .setDefaultValue(true)
                        .build()
        );

        general.addEntry(
                entryBuilder.startBooleanToggle(Text.literal("Disable Rain Splash Particles"), true)
                        .setDefaultValue(true)
                        .build()
        );
        general.addEntry(
    entryBuilder.startIntSlider(
            Text.literal("Minimum Render Distance"),
            PotatoConfig.minRender,
            2,
            16
    )
    .setDefaultValue(4)
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

        return builder.build();
    }
}
