package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.helper.ScreenHelper;

public class SuggestModScreen extends Screen {
    public final Screen lastScreen;

    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 50;

    public SuggestModScreen(Screen lastScreen) {
        this.lastScreen = lastScreen;
    }

    @Override
    public void init() {
        int centerX = this.width / 2;

        super.init();
        this.buttons.clear();
        this.addButton(new ButtonWidget(0, centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, ModContexts.ScreenTexts.downloadButtonText.getContent()));
        this.addButton(new ButtonWidget(1, centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, ModContexts.ScreenTexts.ignoreButtonText.getContent()));
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button.id == 0) {
            if ((ModConfigs.i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) && (ModConfigs.vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher)) {
                ScreenHelper.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
                ScreenHelper.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            } else if (ModConfigs.i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) {
                ScreenHelper.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
            } else if (ModConfigs.vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher) {
                ScreenHelper.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            }
        } else if (button.id == 1) {
            Minecraft.getInstance().openScreen(lastScreen);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        this.renderBackground();

        super.render(mouseX, mouseY, tickDelta);

        this.drawCenteredString(this.textRenderer, ModContexts.ScreenTexts.suggestTitleText.getContent(), this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2), -1);
        this.drawCenteredString(this.textRenderer, ModContexts.ScreenTexts.suggestFailedText.getContent(), this.width / 2, 70, -1);
        this.drawCenteredString(this.textRenderer, ModContexts.ScreenTexts.suggestDownloadNoticeText.getContent(), this.width / 2, 80, -1);
    }
}
