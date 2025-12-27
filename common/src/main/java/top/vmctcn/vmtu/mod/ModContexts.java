package top.vmctcn.vmtu.mod;

import net.minecraft.network.chat.Component;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;

public class ModContexts {
    public static class ModPresent {
        public static boolean i18nUpdateMod = isCoreModClassLoaded("i18nupdatemod.I18nUpdateMod");
        public static boolean vaultPatcher = isCoreModClassLoaded("me.fengming.vaultpatcher_asm.VaultPatcher");

        public static boolean isCoreModClassLoaded(String className) {
            try {
                Class.forName(className);
                return true; // 类存在，coremod已加载
            } catch (ClassNotFoundException e) {
                return false; // 类不存在
            }
        }
    }

    public static class ScreenTexts {
        public static final Component downloadButton = Component.translatable("mco.brokenworld.download");
        public static final Component ignoreButton = Component.translatable("selectWorld.backupJoinSkipButton");
        public static final Component suggestTitleText;
        public static final Component suggestFailedText;
        public static final Component suggestDownloadNoticeText = Component.translatable("vmtranslationupdate.required_mod.warn.download_notice");

        static {
            if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !ModPresent.i18nUpdateMod) && (ModConfigHelper.getConfig().vaultPatcherCheck && !ModPresent.vaultPatcher)) {
                suggestTitleText = Component.translatable("vmtranslationupdate.required_mod.warn.title", "I18nUpdateMod & VaultPatcher");
            } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !ModPresent.i18nUpdateMod) {
                suggestTitleText = Component.translatable("vmtranslationupdate.required_mod.warn.title", "I18nUpdateMod");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !ModPresent.vaultPatcher) {
                suggestTitleText = Component.translatable("vmtranslationupdate.required_mod.warn.title", "VaultPatcher");
            } else {
                suggestTitleText = Component.empty();
            }

            if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !ModPresent.i18nUpdateMod) && (ModConfigHelper.getConfig().vaultPatcherCheck && !ModPresent.vaultPatcher)) {
                suggestFailedText = Component.translatable("vmtranslationupdate.required_mod.warn.detect_failed", "I18nUpdateMod & VaultPatcher");
            } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !ModPresent.i18nUpdateMod) {
                suggestFailedText = Component.translatable("vmtranslationupdate.required_mod.warn.detect_failed", "I18nUpdateMod");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !ModPresent.vaultPatcher) {
                suggestFailedText = Component.translatable("vmtranslationupdate.required_mod.warn.detect_failed", "VaultPatcher");
            } else {
                suggestFailedText = Component.empty();
            }
        }
    }
}