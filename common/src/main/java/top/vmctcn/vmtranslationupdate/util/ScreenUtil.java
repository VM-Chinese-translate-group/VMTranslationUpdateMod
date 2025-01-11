package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;

public class ScreenUtil {
    public static boolean i18nUpdateModPresent = isCoreModClassLoaded("i18nupdatemod.I18nUpdateMod");
    public static boolean vaultPatcherPresent = isCoreModClassLoaded("me.fengming.vaultpatcher_asm.VaultPatcher");
    public static final Text downloadButtonText = Text.translatable("mco.brokenworld.download");
    public static final Text ignoreButtonText = Text.translatable("selectWorld.backupJoinSkipButton");

    public static boolean isCoreModClassLoaded(String className) {
        try {
            Class.forName(className);
            return true; // 类存在，coremod已加载
        } catch (ClassNotFoundException e) {
            return false; // 类不存在
        }
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

    public static Text getSuggestScreenTitle() {
        Text titleText = Text.empty();

        if (ModConfigUtil.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) {
            titleText = Text.translatable("vmtranslationupdate.warn.title", "I18nUpdateMod");
        } else if (ModConfigUtil.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
            titleText = Text.translatable("vmtranslationupdate.warn.title", "VaultPatcher");
        } else if ((ModConfigUtil.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigUtil.getConfig().vaultPatcherCheck && !vaultPatcherPresent)) {
            titleText = Text.translatable("vmtranslationupdate.warn.text", "I18nUpdateMod & VaultPatcher");
        }
        return titleText;
    }

    public static Text getSuggestScreenText() {
        Text context = Text.empty();

        if (ModConfigUtil.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) {
            context = Text.translatable("vmtranslationupdate.warn.text", "I18nUpdateMod");
        } else if (ModConfigUtil.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
            context = Text.translatable("vmtranslationupdate.warn.text", "VaultPatcher");
        } else if ((ModConfigUtil.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigUtil.getConfig().vaultPatcherCheck && !vaultPatcherPresent)) {
            context = Text.translatable("vmtranslationupdate.warn.text", "I18nUpdateMod & VaultPatcher");
        }
        return context;
    }
}
