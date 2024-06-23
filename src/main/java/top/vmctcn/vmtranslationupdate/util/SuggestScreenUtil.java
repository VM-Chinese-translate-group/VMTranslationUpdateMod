package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConfirmChatLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class SuggestScreenUtil {
    private static final boolean i18nUpdateModPresent = CoreModDetection.isClassLoaded("i18nupdatemod.I18nUpdateMod");
    public static boolean vaultPatcherPresent = CoreModDetection.isClassLoaded("me.fengming.vaultpatcher_asm.VaultPatcher");

    public static final Text downloadButtonText = new TranslatableText("vmtranslationupdate.warn.download.button");
    public static final Text quitButtonText = new TranslatableText("vmtranslationupdate.warn.quit.button");

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

    public static void getDownloadUrl(Minecraft client, Screen screen) {
        if ((i18nUpdateModPresent == false) && (vaultPatcherPresent == false)) {
            openUrlOnScreen(client, screen, "https://modrinth.com/mod/i18nupdatemod");
            openUrlOnScreen(client, screen, "https://modrinth.com/mod/vault-patcher");
        } else if (i18nUpdateModPresent == false) {
            openUrlOnScreen(client, screen, "https://modrinth.com/mod/i18nupdatemod");
        } else if (vaultPatcherPresent == false) {
            openUrlOnScreen(client, screen, "https://modrinth.com/mod/vault-patcher");
        }
    }

    public static Text getSuggestScreenTitle() {
        Text titleText = new LiteralText("");

        if ((i18nUpdateModPresent == false) && (vaultPatcherPresent == false)) {
            titleText = new TranslatableText("vmtranslationupdate.warn.title", "I18nUpdateMod & VaultPatcher");
        } else if (i18nUpdateModPresent == false) {
            titleText = new TranslatableText("vmtranslationupdate.warn.title", "I18nUpdateMod");
        } else if (vaultPatcherPresent == false) {
            titleText = new TranslatableText("vmtranslationupdate.warn.title", "VaultPatcher");
        }

        return titleText;
    }

    public static Text getSuggestScreenText() {
        Text context = new LiteralText("");

        if ((i18nUpdateModPresent == false) && (vaultPatcherPresent == false)) {
            context = new TranslatableText("vmtranslationupdate.warn.text", "I18nUpdateMod & VaultPatcher");
        } else if (i18nUpdateModPresent == false) {
            context = new TranslatableText("vmtranslationupdate.warn.text", "I18nUpdateMod");
        } else if (vaultPatcherPresent == false) {
            context = new TranslatableText("vmtranslationupdate.warn.text", "VaultPatcher");
        }

        return context;
    }

    static class CoreModDetection {
        public static boolean isClassLoaded(String className) {
            try {
                Class.forName(className);
                return true; // 类存在，意味着coremod可能被加载了
            } catch (ClassNotFoundException e) {
                return false; // 类不存在
            }
        }
    }
}
