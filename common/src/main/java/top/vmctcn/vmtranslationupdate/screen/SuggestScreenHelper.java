package top.vmctcn.vmtranslationupdate.screen;

import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import top.vmctcn.vmtranslationupdate.config.ModConfigHelper;

public class SuggestScreenHelper {
    public static boolean i18nUpdateModPresent = isCoreModClassLoaded("i18nupdatemod.I18nUpdateMod");
    public static boolean vaultPatcherPresent = isCoreModClassLoaded("me.fengming.vaultpatcher_asm.VaultPatcher");
    public static final Text downloadButtonText = new TranslatableText("mco.brokenworld.download");
    public static final Text ignoreButtonText = new TranslatableText("selectWorld.backupJoinSkipButton");

    public static boolean isCoreModClassLoaded(String className) {
        try {
            Class.forName(className);
            return true; // 类存在，coremod已加载
        } catch (ClassNotFoundException e) {
            return false; // 类不存在
        }
    }

    public static Text getTitle() {
        Text titleText = null;

        if (ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) {
            titleText = new TranslatableText("vmtranslationupdate.warn.title", "I18nUpdateMod");
        } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
            titleText = new TranslatableText("vmtranslationupdate.warn.title", "VaultPatcher");
        } else if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent)) {
            titleText = new TranslatableText("vmtranslationupdate.warn.text", "I18nUpdateMod & VaultPatcher");
        }
        return titleText;
    }

    public static Text getText() {
        Text context = null;

        if (ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) {
            context = new TranslatableText("vmtranslationupdate.warn.text", "I18nUpdateMod");
        } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
            context = new TranslatableText("vmtranslationupdate.warn.text", "VaultPatcher");
        } else if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent)) {
            context = new TranslatableText("vmtranslationupdate.warn.text", "I18nUpdateMod & VaultPatcher");
        }
        return context;
    }
}
