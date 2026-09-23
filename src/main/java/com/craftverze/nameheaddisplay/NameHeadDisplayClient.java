package com.craftverze.nameheaddisplay;

import com.craftverze.nameheaddisplay.render.HudDisplayRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class NameHeadDisplayClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register the HUD renderer callback
        HudRenderCallback.EVENT.register(new HudDisplayRenderer());
    }
}
