package top.vmctcn.vmtu.multiversion.screen.widgets;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.text.Style;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class PlainTextButtonWidget extends ButtonWidget {
    private final TextRenderer textRenderer;
    private final String message;
    private final String underlinedMessage;
    @Nullable
    private String tooltip;

    public PlainTextButtonWidget(int id, int x, int y, int width, int height, String message, TextRenderer textRenderer) {
        super(id, x, y, width, height, message);
        this.textRenderer = textRenderer;
        this.message = message;
        this.underlinedMessage = message;
    }

    public void setTooltip(@Nullable String tooltip) {
        this.tooltip = tooltip;
    }

    public @Nullable String getTooltip() {
        return tooltip;
    }

    @Override
    public void render(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        String buttonText = this.isHovered() ? this.underlinedMessage : this.message;
        drawString(this.textRenderer, buttonText, this.x, this.y, 16777215 | MathHelper.ceil(this.drawOffset * 255.0F) << 24);
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
}
