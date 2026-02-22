//? if <1.20.4 {
/*package top.vmctcn.vmtu.multiversion.screen.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Checkbox;
//? if >=1.20.1 {
import net.minecraft.client.gui.components.Tooltip;
//?} else if >=1.19.2 {
/^import net.minecraft.client.OptionInstance;
^///?}
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CheckboxWidget extends Checkbox {
    //? if <=1.19.2 {
    /^@Nullable
    private Component tooltip;
    ^///?}
    private final OnValueChange onValueChange;

    public CheckboxWidget(int x, int y, Font font, Component component, boolean selected, OnValueChange onValueChange) {
        super(x, y, boxSize(font) + 4 + font.width(component), boxSize(font), component, selected);
        this.onValueChange = onValueChange;
    }

    public static Builder builder(Component component, Font font) {
        return new Builder(component, font);
    }

    //? if <=1.19.2 {
    /^public void setTooltip(@Nullable Component tooltip) {
        this.tooltip = tooltip;
    }

    public @Nullable Component getTooltip() {
        return tooltip;
    }
    ^///?}

    private static int boxSize(Font font) {
        Objects.requireNonNull(font);
        return 9 + 8;
    }

    @Override
    public void onPress() {
        super.onPress();
        this.onValueChange.onValueChange(this, this.selected());
    }

    //? if <=1.19.2 {
    /^@Override
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
    ^///?}

    public interface OnValueChange {
        OnValueChange NOP = (checkbox, selected) -> {
        };

        void onValueChange(Checkbox checkbox, boolean bl);
    }

    public static class Builder {
        private final Component message;
        private final Font font;
        private int x = 0;
        private int y = 0;
        private OnValueChange onValueChange;
        private boolean selected;
        //? if >=1.19.2 {
        @Nullable
        private OptionInstance<Boolean> option;
        //?}
        //? if >=1.20.1 {
        @Nullable
        private Tooltip tooltip;
        //?} else if <=1.19.2 {
        /^@Nullable
        private Component tooltip;
        ^///?}

        Builder(Component component, Font font) {
            this.onValueChange = OnValueChange.NOP;
            this.selected = false;
            //? if >=1.19.2 {
            this.option = null;
            //?}
            this.tooltip = null;
            this.message = component;
            this.font = font;
        }

        public Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder onValueChange(OnValueChange onValueChange) {
            this.onValueChange = onValueChange;
            return this;
        }

        public Builder selected(boolean selected) {
            this.selected = selected;
            //? if >=1.19.2 {
            this.option = null;
            //?}
            return this;
        }

        //? if >=1.19.2 {
        public Builder selected(OptionInstance<Boolean> optionInstance) {
            this.option = optionInstance;
            this.selected = optionInstance.get();
            return this;
        }
        //?}

        public Builder tooltip(/^? if >=1.20.1 {^/Tooltip/^?} else if <=1.19.2 {^//^Component^//^?}^/ tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public CheckboxWidget build() {
            //? if >=1.19.2 {
            OnValueChange onValueChange = this.option == null ? this.onValueChange : (checkboxx, bl) -> {
            //?} else {
            /^OnValueChange onValueChange = (checkboxx, bl) -> {
            ^///?}
                //? if >=1.19.2 {
                this.option.set(bl);
                //?}
                this.onValueChange.onValueChange(checkboxx, bl);
            };

            CheckboxWidget checkbox = new CheckboxWidget(this.x, this.y, this.font, this.message, this.selected, onValueChange);
            checkbox.setTooltip(this.tooltip);
            return checkbox;
        }
    }
}
*///?}