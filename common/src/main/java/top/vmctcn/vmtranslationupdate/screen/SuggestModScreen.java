package top.vmctcn.vmtranslationupdate.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtranslationupdate.config.ModConfigHelper;

public class SuggestModScreen extends Screen {
    public final Screen lastScreen;

    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 50;

    public SuggestModScreen(Screen lastScreen) {
        super(SuggestScreenHelper.getTitle().copy().formatted(Formatting.RED).formatted(Formatting.BOLD));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;

        super.init();

        this.addDrawableChild(new ButtonWidget(centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, SuggestScreenHelper.downloadButtonText, buttonWidget -> {
            if (ModConfigHelper.getConfig().i18nUpdateModCheck && !SuggestScreenHelper.i18nUpdateModPresent) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://modrinth.com/mod/i18nupdatemod");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !SuggestScreenHelper.vaultPatcherPresent) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://modrinth.com/mod/vault-patcher");
            }
        }));
        this.addDrawableChild(new ButtonWidget(centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, SuggestScreenHelper.quitButtonText, buttonWidget -> this.close()));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.render(matrixStack, mouseX, mouseY, delta);

        ScreenHelper.drawCenteredTextWithShadow(matrixStack, this.textRenderer, this.title, this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2), -1);
        ScreenHelper.drawCenteredTextWithShadow(matrixStack, this.textRenderer, SuggestScreenHelper.getText(), this.width / 2, 70, -1);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);

        //Render header and footer separators
        RenderSystem.enableBlend();
        ScreenHelper.resetShaderColor();
        ScreenHelper.drawGuiTexture(matrixStack, 0, 40 - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
        ScreenHelper.drawGuiTexture(matrixStack, 0, this.height - 50, 0.0F, 0.0F, this.width, 2, 32, 2);
        ScreenHelper.resetShaderColor();

    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(this.lastScreen);
    }
}
