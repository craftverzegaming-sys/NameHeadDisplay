package com.craftverze.nameheaddisplay.gui;

import com.craftverze.nameheaddisplay.config.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class NameHeadDisplayScreen extends Screen {
    private final Screen parent;

    private CheckboxWidget headEnabledCheckbox;
    private TextFieldWidget headTextureField;
    private ButtonWidget headSizeButton;
    private ButtonWidget headOutlineButton;
    private ButtonWidget headLayerButton;

    private CheckboxWidget nameEnabledCheckbox;
    private CheckboxWidget nameShadowCheckbox;
    private TextFieldWidget customNameField;
    private CheckboxWidget rainbowNameCheckbox;

    public NameHeadDisplayScreen(Screen parent) {
        super(Text.translatable("mod.nameheaddisplay.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        ModConfig config = ModConfig.getInstance();
        int centerX = this.width / 2;
        int startY = 40;

        this.headEnabledCheckbox = CheckboxWidget.builder(Text.translatable("options.nameheaddisplay.head_enabled"), this.textRenderer)
                .pos(centerX - 150, startY)
                .checked(config.playerHeadEnabled)
                .callback((checkbox, checked) -> config.playerHeadEnabled = checked)
                .build();
        this.addDrawableChild(this.headEnabledCheckbox);

        this.headTextureField = new TextFieldWidget(this.textRenderer, centerX - 150, startY + 24, 300, 20, Text.translatable("options.nameheaddisplay.head_texture"));
        this.headTextureField.setMaxLength(256);
        this.headTextureField.setText(config.headTexture);
        this.headTextureField.setChangedListener(text -> config.headTexture = text);
        this.addDrawableChild(this.headTextureField);

        this.headSizeButton = ButtonWidget.builder(Text.literal("Head size: " + config.headSize), button -> {
            config.headSize = config.headSize >= 32 ? 8 : config.headSize + 1;
            button.setMessage(Text.literal("Head size: " + config.headSize));
        }).dimensions(centerX - 150, startY + 48, 148, 20).build();
        this.addDrawableChild(this.headSizeButton);

        this.headOutlineButton = ButtonWidget.builder(Text.literal("Head outline: " + config.headOutline), button -> {
            config.headOutline = (config.headOutline + 1) % 5;
            button.setMessage(Text.literal("Head outline: " + config.headOutline));
        }).dimensions(centerX + 2, startY + 48, 148, 20).build();
        this.addDrawableChild(this.headOutlineButton);

        this.headLayerButton = ButtonWidget.builder(Text.literal("Head layer: " + config.headLayer), button -> {
            if (config.headLayer.equals("On top")) config.headLayer = "Normal";
            else if (config.headLayer.equals("Normal")) config.headLayer = "Behind";
            else config.headLayer = "On top";
            button.setMessage(Text.literal("Head layer: " + config.headLayer));
        }).dimensions(centerX - 150, startY + 72, 300, 20).build();
        this.addDrawableChild(this.headLayerButton);

        int nameY = startY + 100;
        this.nameEnabledCheckbox = CheckboxWidget.builder(Text.translatable("options.nameheaddisplay.name_enabled"), this.textRenderer)
                .pos(centerX - 150, nameY)
                .checked(config.customNameEnabled)
                .callback((checkbox, checked) -> config.customNameEnabled = checked)
                .build();
        this.addDrawableChild(this.nameEnabledCheckbox);

        this.nameShadowCheckbox = CheckboxWidget.builder(Text.translatable("options.nameheaddisplay.name_shadow"), this.textRenderer)
                .pos(centerX + 10, nameY)
                .checked(config.nameShadow)
                .callback((checkbox, checked) -> config.nameShadow = checked)
                .build();
        this.addDrawableChild(this.nameShadowCheckbox);

        this.customNameField = new TextFieldWidget(this.textRenderer, centerX - 150, nameY + 24, 300, 20, Text.translatable("options.nameheaddisplay.custom_name"));
        this.customNameField.setMaxLength(64);
        this.customNameField.setText(config.customName);
        this.customNameField.setChangedListener(text -> config.customName = text);
        this.addDrawableChild(this.customNameField);

        this.rainbowNameCheckbox = CheckboxWidget.builder(Text.translatable("options.nameheaddisplay.rainbow_name"), this.textRenderer)
                .pos(centerX - 150, nameY + 48)
                .checked(config.rainbowName)
                .callback((checkbox, checked) -> config.rainbowName = checked)
                .build();
        this.addDrawableChild(this.rainbowNameCheckbox);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.nameheaddisplay.save"), button -> {
            ModConfig.save();
            this.close();
        }).dimensions(20, this.height - 30, 100, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.nameheaddisplay.exit"), button -> {
            this.close();
        }).dimensions(this.width - 120, this.height - 30, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 12, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("mod.nameheaddisplay.subtitle"), this.width / 2, 24, 0xAAAAAA);

        int previewBoxWidth = 200;
        int previewBoxHeight = 40;
        int previewX = (this.width - previewBoxWidth) / 2;
        int previewY = this.height - 75;

        context.fill(previewX, previewY, previewX + previewBoxWidth, previewY + previewBoxHeight, 0x80000000);
        context.drawBorder(previewX, previewY, previewBoxWidth, previewBoxHeight, 0xFF555555);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Live Preview: " + (ModConfig.getInstance().customName.isEmpty() ? "Player" : ModConfig.getInstance().customName)), this.width / 2, previewY + 14, 0xFFFFFF);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
