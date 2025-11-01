package top.vmctcn.vmtu.mod;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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
        public static final Text downloadButton = new TranslatableText("mco.brokenworld.download");
        public static final Text ignoreButton = new TranslatableText("selectWorld.backupJoinSkipButton");
        public static final Text suggestTitleText;
        public static final Text suggestFailedText;
        public static final Text suggestDownloadNoticeText = new TranslatableText("vmtranslationupdate.required_mod.warn.download_notice");

        static {
            if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !ModPresent.i18nUpdateMod) && (ModConfigHelper.getConfig().vaultPatcherCheck && !ModPresent.vaultPatcher)) {
                suggestTitleText = new TranslatableText("vmtranslationupdate.required_mod.warn.title", "I18nUpdateMod & VaultPatcher");
            } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !ModPresent.i18nUpdateMod) {
                suggestTitleText = new TranslatableText("vmtranslationupdate.required_mod.warn.title", "I18nUpdateMod");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !ModPresent.vaultPatcher) {
                suggestTitleText = new TranslatableText("vmtranslationupdate.required_mod.warn.title", "VaultPatcher");
            } else {
                suggestTitleText = LiteralText.EMPTY;
            }

            if ((ModConfigHelper.getConfig().i18nUpdateModCheck && !ModPresent.i18nUpdateMod) && (ModConfigHelper.getConfig().vaultPatcherCheck && !ModPresent.vaultPatcher)) {
                suggestFailedText = new TranslatableText("vmtranslationupdate.required_mod.warn.detect_failed", "I18nUpdateMod & VaultPatcher");
            } else if (ModConfigHelper.getConfig().i18nUpdateModCheck && !ModPresent.i18nUpdateMod) {
                suggestFailedText = new TranslatableText("vmtranslationupdate.required_mod.warn.detect_failed", "I18nUpdateMod");
            } else if (ModConfigHelper.getConfig().vaultPatcherCheck && !ModPresent.vaultPatcher) {
                suggestFailedText = new TranslatableText("vmtranslationupdate.required_mod.warn.detect_failed", "VaultPatcher");
            } else {
                suggestFailedText = LiteralText.EMPTY;
            }
        }
    }
}
