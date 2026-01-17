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
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.multiversion.Texts;
import top.vmctcn.vmtu.multiversion.screen.WidgetUtils;
import top.vmctcn.vmtu.multiversion.screen.ScreenUtils;

public abstract class AbstractNotInstallScreen extends Screen {
    public final Screen lastScreen;

    protected static final int HEADER_HEIGHT = 40;
    protected static final int FOOTER_HEIGHT = 50;

    protected final String modName;
    protected final boolean isRequiredCheckBox;
    public Checkbox checkbox;

    protected AbstractNotInstallScreen(Screen lastScreen, String modName, boolean isRequiredCheckBox) {
        super(Texts.translatable("vmtu.required_mod.not_install.title", modName).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
        this.modName = modName;
        this.lastScreen = lastScreen;
        this.isRequiredCheckBox = isRequiredCheckBox;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;

        super.init();

        this.addButtonWidget(WidgetUtils.createButton(ModContexts.ScreenTexts.downloadButton, centerX - 155, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, buttonWidget -> {
            ScreenUtils.openUrlOnScreen(this.minecraft, this, getDownloadUrl());
        }));
        this.addButtonWidget(WidgetUtils.createButton(ModContexts.ScreenTexts.ignoreButton, centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, buttonWidget -> {
            this.onClose();
        }));
        if (isRequiredCheckBox) {
            checkbox = WidgetUtils.createCheckbox(
                    this.font, Texts.translatable("vmtu.required_mod.not_install.checkbox"), getCheckboxTooltip(), centerX - 50, this.height - (FOOTER_HEIGHT / 2) - 50
            );
            setConfigValue(checkbox.selected());
            this.addButtonWidget(checkbox);
        }
    }

    public abstract void setConfigValue(boolean value);

    public abstract String getDownloadUrl();

    public abstract MutableComponent getScreenDescription();

    public abstract MutableComponent getCheckboxTooltip();

    public <T extends AbstractWidget> T addButtonWidget(T widget) {
        //? if >1.16.5 {
        return this.addRenderableWidget(widget);
        //?} else {
        /*return this.addButton(widget);
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
                this.font, getScreenDescription(), this.width / 2, 70, -1);
        ScreenUtils.drawCenteredTextWithShadow(
                /*? if >=1.20.1 {*/
                guiGraphics
                /*?} else {*/
                /*poseStack
                 *//*?}*/,
                this.font, Texts.translatable("vmtu.required_mod.not_install.desc.download"), this.width / 2, 80, -1);
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
