package top.vmctcn.vmtu.mod.helper;

import java.net.URI;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

public class GameEventHelper {
    public static ClickEvent clickOpenUrl(String url) {
        return new ClickEvent.OpenUrl(URI.create(url));
    }

    public static HoverEvent hoverShowText(Component text) {
        return new HoverEvent.ShowText(text);
    }
}
