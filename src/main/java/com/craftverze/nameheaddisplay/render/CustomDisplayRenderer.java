package com.craftverze.nameheaddisplay.render;

import com.craftverze.nameheaddisplay.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

public class CustomDisplayRenderer {

    public static void renderPlayerDisplay(PlayerEntity player, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        ModConfig config = ModConfig.getInstance();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        double distance = client.player.squaredDistanceTo(player);
        if (distance > 1024.0D) return;

        matrices.push();
        matrices.translate(0.0D, player.getHeight() + 0.5D, 0.0D);
        matrices.multiply(client.getEntityRenderDispatcher().getRotation());
        matrices.scale(-0.025F, -0.025F, 0.025F);

        TextRenderer textRenderer = client.textRenderer;

        if (config.customNameEnabled) {
            String nameToDisplay = config.customName.isEmpty() ? player.getName().getString() : config.customName;
            float textWidth = textRenderer.getWidth(nameToDisplay);

            matrices.push();
            matrices.translate(-textWidth / 2.0F, 0, 0);

            if (config.rainbowName) {
                renderRainbowString(matrices, textRenderer, nameToDisplay, config.nameShadow);
            } else {
                textRenderer.draw(nameToDisplay, 0, 0, 0xFFFFFF, config.nameShadow, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, light);
            }
            matrices.pop();
        }

        matrices.pop();
    }

    private static void renderRainbowString(MatrixStack matrices, TextRenderer textRenderer, String text, boolean shadow) {
        long time = System.currentTimeMillis();
        float x = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            float hue = ((time + (i * 200L)) % 3000L) / 3000.0f;
            int rgb = java.awt.Color.HSBtoRGB(hue, 0.8f, 0.9f);

            String s = String.valueOf(c);
            textRenderer.draw(s, x, 0, rgb, shadow, matrices.peek().getPositionMatrix(), null, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
            x += textRenderer.getWidth(s);
        }
    }
}
