package top.vmctcn.vmtu.mod.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.Matrix4f;
import org.apache.commons.lang3.StringUtils;

public class ScreenHelper extends DrawableHelper {
    public static void drawCenteredTextWithShadow(MatrixStack matrixStack, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
        DrawableHelper.drawCenteredTextWithShadow(matrixStack, textRenderer, text.asOrderedText(), centerX, y, color);
    }

    public static void drawGuiTexture(MatrixStack matrixStack, int startX, int startY, int endX, int endY, int z, int colorStart, int colorEnd) {
        fillGradient(matrixStack, startX, startY, endX, endY, colorStart, colorEnd, z);
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