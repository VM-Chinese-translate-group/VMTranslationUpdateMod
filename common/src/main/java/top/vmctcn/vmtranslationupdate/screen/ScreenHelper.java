package top.vmctcn.vmtranslationupdate.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;

public class ScreenHelper {
    public static void drawCenteredTextWithShadow(DrawContext context, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
        context.drawCenteredTextWithShadow(textRenderer, text, centerX, y, color);
    }

    public static void drawGuiTexture(DrawContext context, int startX, int startY, int endX, int endY, int z, int colorStart, int colorEnd) {
        context.fillGradient(startX, startY, endX, endY, z, colorStart, colorEnd);
    }

    public static void resetShaderColor(DrawContext context) {
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void openUrlOnScreen(MinecraftClient client, Screen screen, String url) {
        if (StringUtils.isNotBlank(url) && client != null) {
            client.setScreen(new ConfirmLinkScreen(yes -> {
                if (yes) {
                    Util.getOperatingSystem().open(url);
                }
                client.setScreen(screen);
            }, url, true));
        }
    }
}
