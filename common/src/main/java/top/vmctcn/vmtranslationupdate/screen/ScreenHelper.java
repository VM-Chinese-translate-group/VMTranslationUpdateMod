package top.vmctcn.vmtranslationupdate.screen;

import net.minecraft.client.gui.DrawContext;

public class ScreenHelper {
    public static void drawGuiTexture(DrawContext context, int startX, int startY, int endX, int endY, int z, int colorStart, int colorEnd) {
        context.fillGradient(startX, startY, endX, endY, z, colorStart, colorEnd);
    }

    public static void resetShaderColor(DrawContext context) {
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
