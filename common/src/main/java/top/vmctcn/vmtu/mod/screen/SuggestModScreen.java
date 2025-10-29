package top.vmctcn.vmtu.mod.screen;

import com.mojang.blaze3d.opengl.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.ScreenHelper;

public class SuggestModScreen extends Screen {
    public final Screen lastScreen;

    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 50;

    public SuggestModScreen(Screen lastScreen) {
        super(ModContexts.ScreenTexts.suggestTitleText.copy().formatted(Formatting.RED).formatted(Formatting.BOLD));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;

        super.init();

        this.addDrawableChild(ButtonWidget.builder(ModContexts.ScreenTexts.downloadButton, buttonWidget -> {
            if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) && (ModConfigHelper.getConfig().vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher)) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            }
        }).dimensions(centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20).build());
        this.addDrawableChild(ButtonWidget.builder(ModContexts.ScreenTexts.ignoreButton, buttonWidget -> this.close()).dimensions(centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        ScreenHelper.drawCenteredTextWithShadow(context, this.textRenderer, this.title, this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2), -1);
        ScreenHelper.drawCenteredTextWithShadow(context, this.textRenderer, ModContexts.ScreenTexts.suggestFailedText, this.width / 2, 70, -1);
        ScreenHelper.drawCenteredTextWithShadow(context, this.textRenderer, ModContexts.ScreenTexts.suggestDownloadNoticeText, this.width / 2, 80, -1);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float partial) {
        super.renderBackground(context, mouseX, mouseY, partial);

        //Render header and footer separators
        GlStateManager._enableBlend();
        ScreenHelper.resetShaderColor();
        ScreenHelper.drawGuiTexture(context, Screen.HEADER_SEPARATOR_TEXTURE, 0, HEADER_HEIGHT - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
        ScreenHelper.drawGuiTexture(context, Screen.FOOTER_SEPARATOR_TEXTURE, 0, this.height - FOOTER_HEIGHT, 0.0F, 0.0F, this.width, 2, 32, 2);
        ScreenHelper.resetShaderColor();
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(this.lastScreen);
    }
}
