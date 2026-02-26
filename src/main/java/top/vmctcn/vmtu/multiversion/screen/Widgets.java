package top.vmctcn.vmtu.multiversion.screen;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.text.Text;
import top.vmctcn.vmtu.multiversion.screen.widgets.CheckboxWidget;
import top.vmctcn.vmtu.multiversion.screen.widgets.PlainTextButtonWidget;

public class Widgets {
    public static ButtonWidget createButton(int id, Text text, int x, int y, int width, int height) {
        return new ButtonWidget(id, x, y, width, height, text.getContent());
    }

    public static PlainTextButtonWidget createPlainTextButton(int id, Text text, Text tooltip, int x, int y, int width, int height, TextRenderer textRenderer) {
        PlainTextButtonWidget button = new PlainTextButtonWidget(id, x, y, width, height, text.getContent(), textRenderer);
        button.setTooltip(tooltip.getContent());
        return button;
    }

    public static CheckboxWidget createCheckbox(int id, Text text, Text tooltip, int x, int y, CheckboxWidget.OnValueChange onValueChange) {
        return CheckboxWidget.builder(id, text.getContent()).tooltip(tooltip.getContent()).pos(x, y).onValueChange(onValueChange).build();
    }
}
