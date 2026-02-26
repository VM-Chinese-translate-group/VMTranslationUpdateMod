package top.vmctcn.vmtu.multiversion.screen.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.TextRenderer;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CheckboxWidget extends GuiCheckBox {
    @Nullable
    private String tooltip;
    private final OnValueChange onValueChange;

    public CheckboxWidget(int id, int x, int y, String displayString, boolean isChecked, OnValueChange onValueChange) {
        super(id, x, y, displayString, isChecked);
        this.onValueChange = onValueChange;
    }

    public static Builder builder(int id, String component) {
        return new Builder(id, component);
    }

    public void setTooltip(@Nullable String tooltip) {
        this.tooltip = tooltip;
    }

    public @Nullable String getTooltip() {
        return tooltip;
    }

    private static int boxSize(TextRenderer textRenderer) {
        Objects.requireNonNull(textRenderer);
        return 9 + 8;
    }

    @Override
    public boolean mouseClicked(Minecraft minecraft, int mouseX, int mouseY) {
        this.onValueChange.onValueChange(this, this.isChecked());
        return super.mouseClicked(minecraft, mouseX, mouseY);
    }

    @Override
    public void render(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        super.render(minecraft, mouseX, mouseY, partialTicks);
        if (this.tooltip != null && this.isHovered()) {
            this.renderTooltip(mouseX, mouseY);
        }
    }

    @Override
    public void renderTooltip(int mouseX, int mouseY) {
        super.renderTooltip(mouseX, mouseY);
        Screen screen = Minecraft.getInstance().screen;
        if (screen != null && this.tooltip != null) {
            screen.renderTooltip(this.tooltip, mouseX, mouseY);
        }
    }

    public interface OnValueChange {
        OnValueChange NOP = (checkbox, selected) -> {
        };

        void onValueChange(CheckboxWidget checkbox, boolean bl);
    }

    public static class Builder {
        private final int buttonId;
        private final String message;
        private int xPos = 0;
        private int yPos = 0;
        private OnValueChange onValueChange;
        private boolean selected;
        @Nullable
        private String tooltip;

        Builder(int buttonId, String message) {
            this.buttonId = buttonId;
            this.onValueChange = OnValueChange.NOP;
            this.selected = false;
            this.tooltip = null;
            this.message = message;
        }

        public Builder pos(int x, int y) {
            this.xPos = x;
            this.yPos = y;
            return this;
        }

        public Builder onValueChange(OnValueChange onValueChange) {
            this.onValueChange = onValueChange;
            return this;
        }

        public Builder selected(boolean selected) {
            this.selected = selected;
            return this;
        }

        public Builder tooltip(@Nullable String tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public CheckboxWidget build() {
            OnValueChange onValueChange = (checkbox, bl) -> {
                this.onValueChange.onValueChange(checkbox, bl);
            };

            CheckboxWidget checkbox = new CheckboxWidget(this.buttonId, this.xPos, this.yPos, this.message, this.selected, onValueChange);
            checkbox.setTooltip(this.tooltip);
            return checkbox;
        }
    }
}
