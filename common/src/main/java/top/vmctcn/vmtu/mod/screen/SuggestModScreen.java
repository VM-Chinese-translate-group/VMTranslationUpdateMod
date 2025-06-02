package top.vmctcn.vmtu.mod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.ScreenHelper;

public class SuggestModScreen extends Screen {
    public static boolean i18nUpdateModPresent = isCoreModClassLoaded("i18nupdatemod.I18nUpdateMod");
    public static boolean vaultPatcherPresent = isCoreModClassLoaded("me.fengming.vaultpatcher_asm.VaultPatcher");
    public static final Text downloadButtonText = Text.translatable("mco.brokenworld.download");
    public static final Text ignoreButtonText = Text.translatable("selectWorld.backupJoinSkipButton");

    public final Screen lastScreen;

    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 50;

    public SuggestModScreen(Screen lastScreen) {
        super(getSuggestTitle().copy().formatted(Formatting.RED).formatted(Formatting.BOLD));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;

        super.init();

        this.addDrawableChild(ButtonWidget.builder(downloadButtonText, buttonWidget -> {
            if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent)) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
                ScreenHelper.openUrlOnScreen(this.client, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            }
        }).dimensions(centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20).build());
        this.addDrawableChild(ButtonWidget.builder(ignoreButtonText, buttonWidget -> this.close()).dimensions(centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        ScreenHelper.drawCenteredTextWithShadow(context, this.textRenderer, this.title, this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2), -1);
        ScreenHelper.drawCenteredTextWithShadow(context, this.textRenderer, getSuggestText(), this.width / 2, 70, -1);
        ScreenHelper.drawCenteredTextWithShadow(context, this.textRenderer, Text.translatable("vmtranslationupdate.warn.text2"), this.width / 2, 80, -1);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float partial) {

        super.renderBackground(context, mouseX, mouseY, partial);

        //Render header and footer separators
        RenderSystem.enableBlend();
        ScreenHelper.resetShaderColor(context);
        ScreenHelper.drawGuiTexture(context, Screen.HEADER_SEPARATOR_TEXTURE, 0, HEADER_HEIGHT - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
        ScreenHelper.drawGuiTexture(context, Screen.FOOTER_SEPARATOR_TEXTURE, 0, this.height - FOOTER_HEIGHT, 0.0F, 0.0F, this.width, 2, 32, 2);
        ScreenHelper.resetShaderColor(context);

    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(this.lastScreen);
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
        Text titleText = Text.empty();

        if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent)) {
            titleText = Text.translatable("vmtranslationupdate.warn.title", "I18nUpdateMod & VaultPatcher");
        } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) {
            titleText = Text.translatable("vmtranslationupdate.warn.title", "I18nUpdateMod");
        } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
            titleText = Text.translatable("vmtranslationupdate.warn.title", "VaultPatcher");
        }
        return titleText;
    }

    private static Text getSuggestText() {
        Text context = Text.empty();

        if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent)) {
            context = Text.translatable("vmtranslationupdate.warn.text", "I18nUpdateMod & VaultPatcher");
        } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) {
            context = Text.translatable("vmtranslationupdate.warn.text", "I18nUpdateMod");
        } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
            context = Text.translatable("vmtranslationupdate.warn.text", "VaultPatcher");
        }
        return context;
    }
}
