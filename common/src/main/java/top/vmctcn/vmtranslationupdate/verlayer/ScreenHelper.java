package top.vmctcn.vmtranslationupdate.verlayer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class ScreenHelper {
    public static void drawGuiTexture(DrawContext context, Identifier sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        context.drawTexture(sprite, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static void resetShaderColor(DrawContext context) {
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
