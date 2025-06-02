package top.vmctcn.vmtu.mod.helper;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;

public class GameEventHelper {
    public static ClickEvent clickOpenUrl(String url) {
        return new ClickEvent(ClickEvent.Action.OPEN_URL, url);
    }

    public static HoverEvent hoverShowText(Text text) {
        return new HoverEvent(HoverEvent.Action.SHOW_TEXT, text);
    }
}
