package top.vmctcn.vmtranslationupdate.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;

public class ScreenHelper {
    public static void drawCenteredTextWithShadow(MatrixStack matrixStack, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
        DrawableHelper.drawCenteredTextWithShadow(matrixStack, textRenderer, text.asOrderedText(), centerX, y, color);
    }

    public static void drawGuiTexture(MatrixStack matrixStack, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        DrawableHelper.drawTexture(matrixStack, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static void resetShaderColor() {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
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