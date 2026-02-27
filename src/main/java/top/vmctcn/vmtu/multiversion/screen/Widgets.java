package top.vmctcn.vmtu.multiversion.screen;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import top.vmctcn.vmtu.multiversion.screen.widgets.CheckboxWidget;

public class Widgets {
    public static ButtonWidget createButton(int id, Text text, int x, int y, int width, int height) {
        return new ButtonWidget(id, x, y, width, height, text.getFormattedString());
    }

    public static CheckboxWidget createCheckbox(int id, Text text, Text tooltip, int x, int y) {
        return CheckboxWidget.builder(id, text.getContent()).tooltip(tooltip.getFormattedString()).pos(x, y).build();
    }
}
