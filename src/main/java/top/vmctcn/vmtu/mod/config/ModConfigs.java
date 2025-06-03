package top.vmctcn.vmtu.mod.config;

import net.minecraftforge.common.config.Configuration;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

public class ModConfigs {
    public static final String categoryKey = VMTranslationUpdate.MOD_ID;

    public static Configuration config;

    public static boolean testMode;

    public static boolean autoSwitchLanguage;

    public static boolean autoDownloadVMTranslationPack;

    //public static PackSource translationPackSource;

    public static boolean checkModPackTranslationUpdate;

    public static boolean i18nUpdateModCheck;

    public static boolean vaultPatcherCheck;

    public static void syncConfig() {
        config.setCategoryLanguageKey(categoryKey, "config.vmtranslationupdate.title");
        testMode = config.getBoolean(
                "testMode",
                categoryKey,
                false,
                "开发测试模式",
                "config.vmtranslationupdate.option.testMode"
        );
        autoSwitchLanguage = config.getBoolean(
                "autoSwitchLanguage",
                categoryKey,
                false,
                "自动切换语言",
                "config.vmtranslationupdate.option.autoSwitchLanguage");
        autoDownloadVMTranslationPack = config.getBoolean(
                "autoDownloadVMTranslationPack",
                categoryKey,
                true,
                "自动下载汉化资源包",
                "config.vmtranslationupdate.option.autoDownloadVMTranslationPack");

        checkModPackTranslationUpdate = config.getBoolean(
                "checkModPackTranslationUpdate",
                categoryKey,
                true,
                "自动检测整合包汉化版本更新",
                "config.vmtranslationupdate.option.checkModPackTranslationUpdate");
        i18nUpdateModCheck = config.getBoolean(
                "i18nUpdateModCheck",
                categoryKey,
                true,
                "检测I18nUpdateMod是否已经安装",
                "config.vmtranslationupdate.option.i18nUpdateModCheck"
        );
        vaultPatcherCheck = config.getBoolean(
                "vaultPatcherCheck",
                categoryKey,
                false,
                "检测VaultPatcher是否已经安装",
                "config.vmtranslationupdate.option.vaultPatcherCheck"
        );

        if (config.hasChanged()) {
            config.save();
        }
    }
}
