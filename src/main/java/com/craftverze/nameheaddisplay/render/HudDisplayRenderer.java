package com.craftverze.nameheaddisplay.render;

import com.craftverze.nameheaddisplay.config.ModConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

public class HudDisplayRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        ModConfig config = ModConfig.getInstance();
        if (!config.playerHeadEnabled && !config.customNameEnabled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Size and position right above the experience bar/hotbar
        int boxWidth = 160;
        int boxHeight = 32;
        int x = (screenWidth - boxWidth) / 2;
        int y = screenHeight - 56; // Positioned right above the XP bar

        // Draw background box and border
        context.fill(x, y, x + boxWidth, y + boxHeight, 0x80000000);
        drawCustomBorder(context, x, y, boxWidth, boxHeight, 0xFF555555);

        TextRenderer textRenderer = client.textRenderer;
        String nameToDisplay = config.customName.isEmpty() ? client.player.getName().getString() : config.customName;

        // Draw mini head placeholder box if enabled
        if (config.playerHeadEnabled) {
            context.fill(x + 8, y + 6, x + 26, y + 24, 0xFF333333); // Head background slot
            drawCustomBorder(context, x + 8, y + 6, 18, 18, 0xFF777777);
        }

        // Draw name text next to/below the head
        int textX = config.playerHeadEnabled ? x + 32 : x + 12;
        int textY = y + 12;
        context.drawTextWithShadow(textRenderer, Text.literal(nameToDisplay), textX, textY, 0xFFFFFF);
    }

    private void drawCustomBorder(DrawContext context, int x, int y, int width, int height, int color) {
        context.fill(x, y, x + width, y + 1, color);                  // Top
        context.fill(x, y + height - 1, x + width, y + height, color); // Bottom
        context.fill(x, y, x + 1, y + height, color);                  // Left
        context.fill(x + width - 1, y, x + width, y + height, color); // Right
    }
}
