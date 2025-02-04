package top.vmctcn.vmtranslationupdate.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import top.vmctcn.vmtranslationupdate.config.ModConfigHelper;

public class SuggestModScreen extends Screen {
    public final Screen lastScreen;

    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 50;

    public SuggestModScreen(Screen lastScreen) {
        super(SuggestScreenHelper.getSuggestScreenTitle().copy().formatted(Formatting.RED).formatted(Formatting.BOLD));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;

        super.init();

        this.addDrawableChild(ButtonWidget.builder(SuggestScreenHelper.downloadButtonText, buttonWidget -> {
            if (ModConfigHelper.getConfig().i18nUpdateModCheck && !SuggestScreenHelper.i18nUpdateModPresent) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !SuggestScreenHelper.vaultPatcherPresent) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            }
        }).dimensions(centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20).build());
        this.addDrawableChild(ButtonWidget.builder(SuggestScreenHelper.ignoreButtonText, buttonWidget -> this.close()).dimensions(centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        ScreenHelper.drawCenteredTextWithShadow(context, this.textRenderer, this.title, this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2), -1);
        ScreenHelper.drawCenteredTextWithShadow(context, this.textRenderer, SuggestScreenHelper.getSuggestScreenText(), this.width / 2, 70, -1);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float partial) {
        super.renderBackground(context, mouseX, mouseY, partial);

        //Render header and footer separators
        RenderSystem.enableBlend();
        ScreenHelper.resetShaderColor();
        Identifier headerIdentifier = MinecraftClient.getInstance().world == null ? Screen.HEADER_SEPARATOR_TEXTURE : Screen.INWORLD_HEADER_SEPARATOR_TEXTURE;
        Identifier footerIdentifier = MinecraftClient.getInstance().world == null ? Screen.FOOTER_SEPARATOR_TEXTURE : Screen.INWORLD_FOOTER_SEPARATOR_TEXTURE;
        ScreenHelper.drawGuiTexture(context, headerIdentifier, 0, 40 - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
        ScreenHelper.drawGuiTexture(context, footerIdentifier, 0, this.height - 50, 0.0F, 0.0F, this.width, 2, 32, 2);
        ScreenHelper.resetShaderColor();
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(this.lastScreen);
    }
}