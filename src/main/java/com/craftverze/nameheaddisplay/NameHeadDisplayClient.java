package com.craftverze.nameheaddisplay;

import com.craftverze.nameheaddisplay.config.ModConfig;
import com.craftverze.nameheaddisplay.keybind.KeyBindManager;
import net.fabricmc.api.ClientModInitializer;

public class NameHeadDisplayClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModConfig.load();
        KeyBindManager.register();
        NameHeadDisplayMod.LOGGER.info("NameHeadDisplay Client initialization complete.");
    }
}
