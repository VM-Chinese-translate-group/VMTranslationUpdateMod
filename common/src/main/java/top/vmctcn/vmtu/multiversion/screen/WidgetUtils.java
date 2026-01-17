package top.vmctcn.vmtu.multiversion.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
//? if >=1.20.1 {
import net.minecraft.client.gui.components.Tooltip;
//?}
import net.minecraft.client.gui.screens.Screen;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class WidgetUtils {
    public static Button createButton(Component component, int x, int y, int width, int height, Button.OnPress onPress) {
        //? if >=1.20.1 {
        return Button.builder(component, onPress).bounds(x, y, width, height).build();
        //?} else if <=1.19.2 {
        /*return new Button(x, y, width, height, component, onPress);
        *///?}
    }

    public static Checkbox createCheckbox(Font font, Component component, Component tooltip, int x, int y) {
        //? if >=1.20.4 {
        return Checkbox.builder(component, font).tooltip(Tooltip.create(tooltip)).pos(x, y).build();
        //?} else if 1.20.1 {
        /*Checkbox checkbox = new Checkbox(x, y, calculateWidth(calculateWidth(component, font), component, font), calculateHeight(font), component, false);
        checkbox.setTooltip(Tooltip.create(tooltip));
        return checkbox;
        *///?} else if <=1.19.2 {
        /*WidgetCheckbox checkbox = new WidgetCheckbox(x, y, calculateWidth(calculateWidth(component, font), component, font), calculateHeight(font), component, false);
        checkbox.setTooltip(tooltip);
        return checkbox;
        *///?}
    }


    //? if <=1.20.1 {
    /*private static int calculateWidth(int max, Component component, Font font) {
        return Math.min(calculateWidth(component, font), max);
    }

    private static int calculateHeight(Font font) {
        return Math.max(getCheckboxSize(font), font.lineHeight);
    }

    static int calculateWidth(Component text, Font font) {
        return getCheckboxSize(font) + 4 + font.width(text);
    }

    public static int getCheckboxSize(Font font) {
        return 17;
    }
    *///?}

    //? if <=1.19.2 {
    /*public static class WidgetCheckbox extends Checkbox {
        @Nullable
        private Component tooltip;

        public WidgetCheckbox(int x, int y, int width, int height, Component component, boolean bl) {
            super(x, y, width, height, component, bl);
        }

        public void setTooltip(@Nullable Component tooltip) {
            this.tooltip = tooltip;
        }

        @Override
        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
            super.renderButton(poseStack, mouseX, mouseY, partialTicks);
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
}
