package top.vmctcn.vmtu.mod.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;

public class ScreenHelper {
    public static void drawCenteredTextWithShadow(GuiGraphics context, Font textRenderer, Component text, int centerX, int y, int color) {
        context.drawCenteredString(textRenderer, text, centerX, y, color);
    }

    public static void drawGuiTexture(GuiGraphics context, Identifier sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        context.blit(RenderPipelines.GUI_TEXTURED, sprite, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static void resetShaderColor() {
        //NO-OP
    }

    public static void openUrlOnScreen(Minecraft client, Screen screen, String url) {
        if (StringUtils.isNotBlank(url) && client != null) {
            client.setScreen(new ConfirmLinkScreen(yes -> {
                if (yes) {
                    Util.getPlatform().openUri(url);
                }
                client.setScreen(screen);
            }, url, true));
        }
    }
}
