package top.vmctcn.vmtu.mod.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConfirmChatLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class ScreenHelper {
    public static void openUrlOnScreen(Minecraft client, Screen screen, String url) {
        if (StringUtils.isNotBlank(url) && client != null) {
            client.openScreen(new ConfirmChatLinkScreen((yes, i) -> {
                if (yes) {
                    try {
                        Desktop.getDesktop().browse(URI.create(url));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                client.openScreen(screen);
            }, url , 0, true));
        }
    }
}
