package top.vmctcn.vmtu.mod.helper;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;

import java.net.URI;

public class GameEventHelper {
    public static ClickEvent clickOpenUrl(String url) {
        return new ClickEvent.OpenUrl(URI.create(url));
    }

    public static HoverEvent hoverShowText(Text text) {
        return new HoverEvent.ShowText(text);
    }
}
