package top.vmctcn.vmtu.mod.screen;

//? if >=1.21.5 {
import com.mojang.blaze3d.opengl.GlStateManager;
//?} else if <=1.21.4 && >=1.18.2 {
/*import com.mojang.blaze3d.systems.RenderSystem;
*///?}
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
//? if >=1.20.1 {
import net.minecraft.client.gui.GuiGraphics;
//?} else if <=1.19.2 {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?}
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.multiversion.ScreenUtils;

public class SuggestModScreen extends Screen {
    public final Screen lastScreen;

    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 50;

    public SuggestModScreen(Screen lastScreen) {
        super(ModContexts.ScreenTexts.suggestTitleText.copy().withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;

        super.init();

        //? if >=1.20.1 {
        this.addRenderableWidget(Button.builder(ModContexts.ScreenTexts.downloadButton, buttonWidget -> {
            if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) && (ModConfigHelper.getConfig().vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher)) {
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) {
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher) {
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            }
        }).bounds(centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20).build());
        this.addRenderableWidget(Button.builder(ModContexts.ScreenTexts.ignoreButton, buttonWidget -> this.onClose()).bounds(centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20).build());
        //?} else if <=1.19.2 && >1.16.5 {
        /*this.addRenderableWidget(new Button(centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, ModContexts.ScreenTexts.downloadButton, buttonWidget -> {
            if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) && (ModConfigHelper.getConfig().vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher)) {
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) {
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher) {
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            }
        }));
        this.addRenderableWidget(new Button(centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, ModContexts.ScreenTexts.ignoreButton, buttonWidget -> this.onClose()));
        *///?} else {
        /*this.addButton(new Button(centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, ModContexts.ScreenTexts.downloadButton, buttonWidget -> {
            if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) && (ModConfigHelper.getConfig().vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher)) {
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod) {
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher) {
                ScreenUtils.openUrlOnScreen(this.minecraft, this, "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/");
            }
        }));
        this.addButton(new Button(centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, ModContexts.ScreenTexts.ignoreButton, buttonWidget -> this.onClose()));
        *///?}
    }

    @Override
    public void render(
            /*? if >=1.20.1 {*/
            GuiGraphics guiGraphics
            /*?} else {*/
            /*PoseStack poseStack
            *//*?}*/,
            int mouseX, int mouseY, float delta
    ) {
        //? if 1.20.1 {
        /*this.renderBackground(guiGraphics);
        *///?} else if <=1.19.2 {
        /*this.renderBackground(poseStack);
        *///?}

        //? if >=1.20.1 {
        super.render(guiGraphics, mouseX, mouseY, delta);
        //?} else if <=1.19.2 {
        /*super.render(poseStack, mouseX, mouseY, delta);
        *///?}

        ScreenUtils.drawCenteredTextWithShadow(
                /*? if >=1.20.1 {*/
                guiGraphics
                /*?} else {*/
                /*poseStack
                *//*?}*/,
                this.font, this.title, this.width / 2, (HEADER_HEIGHT / 2) - (this.font.lineHeight / 2), -1);
        ScreenUtils.drawCenteredTextWithShadow(
                /*? if >=1.20.1 {*/
                guiGraphics
                /*?} else {*/
                /*poseStack
                *//*?}*/,
                this.font, ModContexts.ScreenTexts.suggestFailedText, this.width / 2, 70, -1);
        ScreenUtils.drawCenteredTextWithShadow(
                /*? if >=1.20.1 {*/
                guiGraphics
                /*?} else {*/
                /*poseStack
                *//*?}*/,
                this.font, ModContexts.ScreenTexts.suggestDownloadNoticeText, this.width / 2, 80, -1);
    }

    //? if >=1.18.2 {
    @Override
    public void renderBackground(
            /*? if >=1.20.4 {*/
            GuiGraphics guiGraphics, int mouseX, int mouseY, float partial
            /*?} else if 1.20.1 {*/
            /*GuiGraphics guiGraphics
            *//*?} else if <=1.19.2 && >=1.18.2 {*/
            /*PoseStack poseStack
            *//*?}*/
    ) {
        //? if >=1.20.4 {
        super.renderBackground(guiGraphics, mouseX, mouseY, partial);
        //?} else if 1.20.1 {
        /*super.renderBackground(guiGraphics);
        *///?} else if <=1.19.2 && >=1.18.2 {
        /*super.renderBackground(poseStack);
        *///?}

        //Render header and footer separators
        //? if >=1.21.5 {
        GlStateManager._enableBlend();
        //?} else if <=1.21.4 && >=1.18.2 {
        /*RenderSystem.enableBlend();
        *///?}
        ScreenUtils.resetShaderColor();
        //? if >=1.20.6 {
        ScreenUtils.drawGuiTexture(guiGraphics, Screen.HEADER_SEPARATOR, 0, HEADER_HEIGHT - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
        ScreenUtils.drawGuiTexture(guiGraphics, Screen.FOOTER_SEPARATOR, 0, this.height - FOOTER_HEIGHT, 0.0F, 0.0F, this.width, 2, 32, 2);
        //?} else if <=1.20.4 && >=1.20.1 {
        /*ScreenUtils.drawGuiTexture(guiGraphics, 0, HEADER_HEIGHT, this.width, HEADER_HEIGHT + 4, 0, -16777216, 0);
        ScreenUtils.drawGuiTexture(guiGraphics, 0, this.height - FOOTER_HEIGHT - 4, this.width, this.height - FOOTER_HEIGHT, 0, 0, -16777216);
        *///?} else if <=1.19.2 && >=1.18.2 {
        /*ScreenUtils.drawGuiTexture(poseStack, 0, HEADER_HEIGHT, this.width, HEADER_HEIGHT + 4, 0, -16777216, 0);
        ScreenUtils.drawGuiTexture(poseStack, 0, this.height - FOOTER_HEIGHT - 4, this.width, this.height - FOOTER_HEIGHT, 0, 0, -16777216);
        *///?}
        ScreenUtils.resetShaderColor();
    }
    //?}

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(this.lastScreen);
    }
}
