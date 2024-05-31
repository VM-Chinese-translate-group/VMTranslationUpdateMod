package top.vmctcn.vmtranslationupdate.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;

public class ScreenAfterInitEvent {
    protected static boolean i18nUpdateModPresent = false;
    protected static boolean vaultPatcherPresent = false;
    protected static boolean firstTitleScreenShown = false;
    protected static final Text downloadButtonText = Text.translatable("vmtranslationupdate.warn.download.button");
    protected static final Text quitButtonText = Text.translatable("vmtranslationupdate.warn.quit.button");

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        if (ModConfigUtil.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent ) {
            MinecraftClient.getInstance().setScreen(new I18nUpdateModScreen(screen));
        } else if (ModConfigUtil.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
            MinecraftClient.getInstance().setScreen(new VaultPatcherScreen(screen));
        }

        firstTitleScreenShown = true;
    }

    public static void checkModsLoaded() {
        try {
            Class.forName("i18nupdatemod.I18nUpdateMod");
            i18nUpdateModPresent = true;
        } catch (ClassNotFoundException e) {
            i18nUpdateModPresent = false;
        }

        try {
            Class.forName("me.fengming.vaultpatcher_asm.VaultPatcher");
            vaultPatcherPresent = true;
        } catch (ClassNotFoundException e) {
            vaultPatcherPresent = false;
        }
    }

    protected static void openUrlOnScreen(MinecraftClient client, Screen screen, String url) {
        if (StringUtils.isNotBlank(url) && client != null) {
            client.setScreen(new ConfirmLinkScreen(yes -> {
                if (yes) {
                    Util.getOperatingSystem().open(url);
                }
                client.setScreen(screen);
            }, url, true));
        }
    }

    protected static void resetShaderColor(DrawContext context) {
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
