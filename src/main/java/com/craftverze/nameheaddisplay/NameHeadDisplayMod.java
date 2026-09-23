package com.craftverze.nameheaddisplay;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameHeadDisplayMod implements ModInitializer {
    public static final String MOD_ID = "nameheaddisplay";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("NameHeadDisplay by Craftverze initialized successfully for Minecraft 1.21.11.");
    }
}
