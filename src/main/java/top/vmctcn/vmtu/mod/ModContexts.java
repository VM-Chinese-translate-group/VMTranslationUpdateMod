package top.vmctcn.vmtu.mod;

import net.minecraft.text.Text;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.multiversion.Texts;

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
        public static final Text downloadButtonText = Texts.translatable("vmtranslationupdate.warn.download.button");
        public static final Text ignoreButtonText = Texts.translatable("vmtranslationupdate.warn.ignore.button");
        public static final Text suggestTitleText;
        public static final Text suggestFailedText;
        public static final Text suggestDownloadNoticeText = Texts.translatable("vmtranslationupdate.required_mod.warn.download_notice");

        static {
            if ((ModConfigs.i18nUpdateModCheck && !ModPresent.i18nUpdateMod) && (ModConfigs.vaultPatcherCheck && !ModPresent.vaultPatcher)) {
                suggestTitleText = Texts.translatable("vmtranslationupdate.required_mod.warn.title", "I18nUpdateMod & VaultPatcher");
            } else if (ModConfigs.i18nUpdateModCheck && !ModPresent.i18nUpdateMod) {
                suggestTitleText = Texts.translatable("vmtranslationupdate.required_mod.warn.title", "I18nUpdateMod");
            } else if (ModConfigs.vaultPatcherCheck && !ModPresent.vaultPatcher) {
                suggestTitleText = Texts.translatable("vmtranslationupdate.required_mod.warn.title", "VaultPatcher");
            } else {
                suggestTitleText = Texts.empty();
            }

            if ((ModConfigs.i18nUpdateModCheck && !ModPresent.i18nUpdateMod) && (ModConfigs.vaultPatcherCheck && !ModPresent.vaultPatcher)) {
                suggestFailedText = Texts.translatable("vmtranslationupdate.required_mod.warn.detect_failed", "I18nUpdateMod & VaultPatcher");
            } else if (ModConfigs.i18nUpdateModCheck && !ModPresent.i18nUpdateMod) {
                suggestFailedText = Texts.translatable("vmtranslationupdate.required_mod.warn.detect_failed", "I18nUpdateMod");
            } else if (ModConfigs.vaultPatcherCheck && !ModPresent.vaultPatcher) {
                suggestFailedText = Texts.translatable("vmtranslationupdate.required_mod.warn.detect_failed", "VaultPatcher");
            } else {
                suggestFailedText = Texts.empty();
            }
        }
    }
}
