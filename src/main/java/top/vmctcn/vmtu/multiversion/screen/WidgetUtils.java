package top.vmctcn.vmtu.multiversion.screen;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class WidgetUtils {
    public static ButtonWidget createButton(int id, Text text, int x, int y, int width, int height) {
        return new ButtonWidget(id, x, y, width, height, text.getContent());
    }
}
