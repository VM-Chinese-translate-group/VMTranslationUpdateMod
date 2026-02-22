//? if <=1.19.2 {
/*package top.vmctcn.vmtu.multiversion.screen.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
//? if >=1.18.2 {
import net.minecraft.client.gui.components.PlainTextButton;
//?}
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class PlainTextButtonWidget extends /^? if >=1.18.2 {^/PlainTextButton/^?} else {^//^Button^//^?}^/ {
    //? if 1.16.5 {
    /^private final Font font;
    private final Component message;
    private final Component underlinedMessage;
    ^///?}
    @Nullable
    private Component tooltip;

    public PlainTextButtonWidget(int x, int y, int width, int height, Component component, OnPress onPress, Font font) {
        super(x, y, width, height, component, onPress/^? if >=1.18.2 {^/, font/^?}^/);
        //? if 1.16.5 {
        /^this.font = font;
        this.message = component;
        this.underlinedMessage = ComponentUtils.mergeStyles(component.copy(), Style.EMPTY.withUnderlined(true));
        ^///?}
    }

    public void setTooltip(@Nullable Component tooltip) {
        this.tooltip = tooltip;
    }

    public @Nullable Component getTooltip() {
        return tooltip;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        //? if 1.16.5 {
        /^Component component = (this.isHovered() || this.isFocused()) ? this.underlinedMessage : this.message;
        drawString(poseStack, this.font, component, this.x, this.y, 16777215 | Mth.ceil(this.alpha * 255.0F) << 24);
        ^///?} else {
        super.renderButton(poseStack, mouseX, mouseY, partialTicks);
        //?}
        if (this.tooltip != null && this.isHovered) {
            this.renderToolTip(poseStack, mouseX, mouseY);
        }
    }

    @Override
    public void renderToolTip(PoseStack poseStack, int x, int y) {
        super.renderToolTip(poseStack, x, y);
        Screen screen = Minecraft.getInstance().screen;
        if (screen != null && this.tooltip != null) {
            screen.renderTooltip(poseStack, this.tooltip, x, y);
        }
    }
}
*///?}
