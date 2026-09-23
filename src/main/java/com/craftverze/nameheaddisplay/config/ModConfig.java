package com.craftverze.nameheaddisplay.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("nameheaddisplay.json");

    private static ModConfig INSTANCE = new ModConfig();

    public boolean playerHeadEnabled = true;
    public String headTexture = "";
    public int headSize = 13;
    public int headOutline = 0;
    public String headLayer = "On top";

    public boolean customNameEnabled = true;
    public boolean nameShadow = true;
    public String customName = "Craftverze";
    public boolean rainbowName = false;

    public boolean experienceBarEnabled = false;
    public float experienceBarScale = 1.0f;
    public String experienceBarPosition = "Default";

    public static ModConfig getInstance() {
        return INSTANCE;
    }

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                ModConfig loaded = GSON.fromJson(json, ModConfig.class);
                if (loaded != null) {
                    INSTANCE = loaded;
                }
            } catch (Exception e) {
                e.printStackTrace();
                INSTANCE = new ModConfig();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
