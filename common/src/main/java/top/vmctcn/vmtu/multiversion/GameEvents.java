package top.vmctcn.vmtu.multiversion;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
//? if >=1.21.5 {
import java.net.URI;
//?}

public class GameEvents {
    public static ClickEvent clickOpenUrl(String url) {
        //? if >=1.21.5 {
        return new ClickEvent.OpenUrl(URI.create(url));
        //?} else if <=1.21.4 {
        /*return new ClickEvent(ClickEvent.Action.OPEN_URL, url);
        *///?}
    }

    public static HoverEvent hoverShowText(Component text) {
        //? if >=1.21.5 {
        return new HoverEvent.ShowText(text);
        //?} else if <=1.21.4 {
        /*return new HoverEvent(HoverEvent.Action.SHOW_TEXT, text);
        *///?}
    }
}
