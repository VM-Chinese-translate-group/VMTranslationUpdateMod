package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.helper.ScreenHelper;

public class SuggestModScreen extends Screen {
    public static boolean i18nUpdateModPresent = isCoreModClassLoaded("i18nupdatemod.I18nUpdateMod");
    public static boolean vaultPatcherPresent = isCoreModClassLoaded("me.fengming.vaultpatcher_asm.VaultPatcher");

    public static final Text downloadButtonText = new TranslatableText("vmtranslationupdate.warn.download.button");
    public static final Text ignoreButtonText = new TranslatableText("vmtranslationupdate.warn.ignore.button");

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
        this.addButton(new ButtonWidget(0, centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, downloadButtonText.getContent()));
        this.addButton(new ButtonWidget(1, centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, ignoreButtonText.getContent()));

    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button.id == 0) {
            if ((ModConfigs.i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigs.vaultPatcherCheck && !vaultPatcherPresent)) {
                ScreenHelper.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
                ScreenHelper.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            } else if (ModConfigs.i18nUpdateModCheck && !i18nUpdateModPresent) {
                ScreenHelper.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
            } else if (ModConfigs.vaultPatcherCheck && !vaultPatcherPresent) {
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

        this.drawCenteredString(this.textRenderer, getSuggestTitle().getContent(), this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2), -1);
        this.drawCenteredString(this.textRenderer, getSuggestText().getContent(), this.width / 2, 70, -1);
    }

    public static boolean isCoreModClassLoaded(String className) {
        try {
            Class.forName(className);
            return true; // 类存在，coremod已加载
        } catch (ClassNotFoundException e) {
            return false; // 类不存在
        }
    }

    private static Text getSuggestTitle() {
        Text titleText = new LiteralText("");

        if ((ModConfigs.i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigs.vaultPatcherCheck && !vaultPatcherPresent)) {
            titleText = new TranslatableText("vmtranslationupdate.warn.title", "I18nUpdateMod & VaultPatcher");
        } else if (ModConfigs.i18nUpdateModCheck && !i18nUpdateModPresent) {
            titleText = new TranslatableText("vmtranslationupdate.warn.title", "I18nUpdateMod");
        } else if (ModConfigs.vaultPatcherCheck && !vaultPatcherPresent) {
            titleText = new TranslatableText("vmtranslationupdate.warn.title", "VaultPatcher");
        }
        return titleText;
    }

    private static Text getSuggestText() {
        Text context = new LiteralText("");

        if ((ModConfigs.i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigs.vaultPatcherCheck && !vaultPatcherPresent)) {
            context = new TranslatableText("vmtranslationupdate.warn.text", "I18nUpdateMod & VaultPatcher");
        } else if (ModConfigs.i18nUpdateModCheck && !i18nUpdateModPresent) {
            context = new TranslatableText("vmtranslationupdate.warn.text", "I18nUpdateMod");
        } else if (ModConfigs.vaultPatcherCheck && !vaultPatcherPresent) {
            context = new TranslatableText("vmtranslationupdate.warn.text", "VaultPatcher");
        }
        return context;
    }
}
