package top.vmctcn.vmtu.multiversion.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConfirmChatLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class ScreenUtils {
    public static void drawCenteredTextWithShadow(TextRenderer textRenderer, Text text, int centerX, int y, int color) {
        textRenderer.drawWithShadow(text.getFormattedString(), (float)(centerX - textRenderer.getWidth(text.getContent()) / 2), (float)y, color);
    }

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
