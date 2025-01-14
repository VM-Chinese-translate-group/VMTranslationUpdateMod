package top.vmctcn.vmtranslationupdate.screen;

import net.minecraft.text.Text;
import top.vmctcn.vmtranslationupdate.config.ModConfigHelper;

public class SuggestScreenHelper {
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

    public static Text getSuggestScreenTitle() {
        Text titleText = Text.empty();

        if (ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) {
            titleText = Text.translatable("vmtranslationupdate.warn.title", "I18nUpdateMod");
        } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
            titleText = Text.translatable("vmtranslationupdate.warn.title", "VaultPatcher");
        } else if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent)) {
            titleText = Text.translatable("vmtranslationupdate.warn.text", "I18nUpdateMod & VaultPatcher");
        }
        return titleText;
    }

    public static Text getSuggestScreenText() {
        Text context = Text.empty();

        if (ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) {
            context = Text.translatable("vmtranslationupdate.warn.text", "I18nUpdateMod");
        } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent) {
            context = Text.translatable("vmtranslationupdate.warn.text", "VaultPatcher");
        } else if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !i18nUpdateModPresent) && (ModConfigHelper.getConfig().vaultPatcherCheck && !vaultPatcherPresent)) {
            context = Text.translatable("vmtranslationupdate.warn.text", "I18nUpdateMod & VaultPatcher");
        }
        return context;
    }
}
