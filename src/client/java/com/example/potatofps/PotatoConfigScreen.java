package com.example.potatofps;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class PotatoConfigScreen {

    public static Screen create(Screen parent) {

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("Potato FPS Settings"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        builder.getOrCreateCategory(Text.literal("General"))
                .addEntry(entryBuilder.startBooleanToggle(
                        Text.literal("Enable Potato Mode"),
                        true
                ).setDefaultValue(true).build());

        return builder.build();
    }
}
