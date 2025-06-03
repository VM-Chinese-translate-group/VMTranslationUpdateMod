package top.vmctcn.vmtu.mod.helper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ConfirmChatLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;

public class ScreenHelper extends DrawableHelper {
    public static void drawCenteredTextWithShadow(MatrixStack matrixStack, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
        DrawableHelper.drawCenteredText(matrixStack, textRenderer, text, centerX, y, color);
    }

    public static void openUrlOnScreen(MinecraftClient client, Screen screen, String url) {
        if (StringUtils.isNotBlank(url) && client != null) {
            client.openScreen(new ConfirmChatLinkScreen(yes -> {
                if (yes) {
                    Util.getOperatingSystem().open(url);
                }
                client.openScreen(screen);
            }, url, true));
        }
    }
}
