package top.vmctcn.vmtu.multiversion.screen;

//? if <=1.21.5 {
/*import com.mojang.blaze3d.systems.RenderSystem;
*///?}

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else if >=1.20.1 && <=1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} else if <=1.19.2 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
*///?}
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
//? if <=1.21.5 && >=1.21.4 {
/*import net.minecraft.client.renderer.RenderType;
*///?}
//? if >=1.21.8 {
import net.minecraft.client.renderer.RenderPipelines;
//?}
import net.minecraft.network.chat.Component;
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?} else if >=1.20.6 && <=1.21.10 {
/*import net.minecraft.resources.ResourceLocation;
*///?}
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;

public class ScreenUtils /*? if <=1.19.2 {*//*extends GuiComponent*//*?}*/ {
    public static void drawCenteredTextWithShadow(
            /*? if >=26.1 {*/
            GuiGraphicsExtractor graphics, Font font, Component text, int centerX, int y, int color
            /*?} else if >=1.20.1 && <=1.21.11 {*/
            /*GuiGraphics graphics, Font font, Component text, int centerX, int y, int color
            *//*?} else if <=1.19.2 {*/
            /*PoseStack poseStack, Font font, Component text, int centerX, int y, int color
            *//*?}*/
    ) {
        //? if >=26.1 {
        graphics.centeredText(font, text, centerX, y, color);
        //?} else if >=1.20.1 && <=1.21.11 {
        /*graphics.drawCenteredString(font, text, centerX, y, color);
        *///?} else if <=1.19.2 {
        /*drawCenteredString(poseStack, font, text, centerX, y, color);
        *///?}
    }

    public static void drawGuiTexture(
            /*? if >=26.1 {*/
            GuiGraphicsExtractor graphics, Identifier sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight
            /*?} else if 1.21.11 {*/
            /*GuiGraphics graphics, Identifier sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight
            *//*?} else if >=1.20.6 && <=1.21.10 {*/
            /*GuiGraphics graphics, ResourceLocation sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight
            *//*?} else if <=1.20.4 && >=1.20.1 {*/
            /*GuiGraphics graphics, int startX, int startY, int endX, int endY, int z, int colorStart, int colorEnd
            *//*?} else if <=1.19.2 && >=1.18.2 {*/
            /*PoseStack poseStack, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int z
            *//*?} else if <=1.18.2 {*/
            /*?}*/
    ) {
        //? if >=1.21.8 {
        graphics.blit(RenderPipelines.GUI_TEXTURED, sprite, x, y, u, v, width, height, textureWidth, textureHeight);
        //?} else if <=1.21.5 && >=1.21.4 {
        /*graphics.blit(RenderType::guiTextured, sprite, x, y, u, v, width, height, textureWidth, textureHeight);
        *///?} else if <=1.21.1 && >=1.20.6 {
        /*graphics.blit(sprite, x, y, u, v, width, height, textureWidth, textureHeight);
        *///?} else if <=1.20.4 && >=1.20.1 {
        /*graphics.fillGradient(startX, startY, endX, endY, z, colorStart, colorEnd);
        *///?} else if <=1.19.2 && >=1.18.2 {
        /*fillGradient(poseStack, startX, startY, endX, endY, colorStart, colorEnd, z);
        *///?}
    }

    //? if >=1.18.2 {
    public static void resetShaderColor() {
        //? if <=1.21.5 {
        /*RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        *///?}
    }
    //?}

    public static void openUrlOnScreen(Minecraft client, Screen screen, String url) {
        if (StringUtils.isNotBlank(url) && client != null) {
            openScreen(client, new ConfirmLinkScreen(yes -> {
                if (yes) {
                    Util.getPlatform().openUri(url);
                }
                openScreen(client, screen);
            }, url, true));
        }
    }

    public static void openScreen(Minecraft client, Screen screen) {
        //? if <26.2 {
        /*client.setScreen(screen);
        *///?} else {
        client.gui.setScreen(screen);
        //?}
    }
}
