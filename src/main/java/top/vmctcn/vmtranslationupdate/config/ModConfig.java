package top.vmctcn.vmtranslationupdate.config;

import net.minecraftforge.common.config.Configuration;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

public class ModConfig {

    public static Configuration config;

    public static boolean autoSwitchLanguage;
    public static boolean checkModPackTranslationUpdate;
    public static boolean autoDownloadVMTranslationPack;
    public static boolean autoInstallVMTranslationPack;
    public static boolean displayTips;
    public static boolean playerNameCheck;

    public static String switchLanguage;
    public static String modPackTranslationUpdateCheckUrl;
    public static String modPackTranslationUrl;
    public static String tipsUrl;
    public static String modPackTranslationVersion;
    public static int tipsMinutes;
    public static String translationPackName;
    public static String translationPackUrl;
    public static String nameUrl;

    public static void syncConfig() {
        config.setCategoryLanguageKey(VMTranslationUpdate.MOD_ID, "config.vmtranslationupdate.title");
        autoSwitchLanguage = config.getBoolean(
                "autoSwitchLanguage",
                VMTranslationUpdate.MOD_ID,
                false,
                "是否自动根据地区切换语言",
                "config.vmtranslationupdate.option.autoSwitchLanguage");
        checkModPackTranslationUpdate = config.getBoolean(
                "checkModPackTranslationUpdate",
                VMTranslationUpdate.MOD_ID,
                true,
                "是否自动检测整合包汉化版本更新",
                "config.vmtranslationupdate.option.checkModPackTranslationUpdate");
        autoDownloadVMTranslationPack = config.getBoolean(
                "autoDownloadVMTranslationPack",
                VMTranslationUpdate.MOD_ID,
                true,
                "是否自动下载VM汉化资源包",
                "config.vmtranslationupdate.option.autoDownloadVMTranslationPack");
        autoInstallVMTranslationPack = config.getBoolean(
                "autoInstallVMTranslationPack",
                VMTranslationUpdate.MOD_ID,
                true,
                "是否自动安装VM汉化资源包",
                "config.vmtranslationupdate.option.autoInstallVMTranslationPack");
        displayTips = config.getBoolean(
                "displayTips",
                VMTranslationUpdate.MOD_ID,
                true,
                "是否显示知识内容",
                "config.vmtranslationupdate.option.displayTips");
        playerNameCheck = config.getBoolean(
                "playerNameCheck",
                VMTranslationUpdate.MOD_ID,
                true,
                "是否检测玩家名称",
                "config.vmtranslationupdate.option.playerNameCheck");

        switchLanguage = config.getString(
                "switchLanguage",
                VMTranslationUpdate.MOD_ID,
                "zh_cn",
                "切换语言为",
                "config.vmtranslationupdate.option.switchLanguage");
        modPackTranslationUpdateCheckUrl = config.getString(
                "modPackTranslationUpdateCheckUrl",
                VMTranslationUpdate.MOD_ID,
                "https://vmct-cn.top/rad/update.txt",
                "更新检测链接",
                "config.vmtranslationupdate.option.modPackTranslationUpdateCheckUrl");
        modPackTranslationUrl = config.getString(
                "modPackTranslationUrl",
                VMTranslationUpdate.MOD_ID,
                "https://vmct-cn.top/modpacks/rad/",
                "下载链接",
                "config.vmtranslationupdate.option.modPackTranslationUrl");
        tipsUrl = config.getString(
                "tipsUrl",
                VMTranslationUpdate.MOD_ID,
                "https://vmct-cn.top/tips.txt",
                "获取知识内容的链接",
                "config.vmtranslationupdate.option.tipsUrl");
        modPackTranslationVersion = config.getString(
                "modPackTranslationVersion",
                VMTranslationUpdate.MOD_ID,
                "1.0.0",
                "整合包翻译版本",
                "config.vmtranslationupdate.option.modPackTranslationVersion");
        tipsMinutes = config.getInt(
                "tipsMinutes",
                VMTranslationUpdate.MOD_ID,
                25,
                Integer.MAX_VALUE,
                Integer.MIN_VALUE,
                "发送知识的时间间隔（分钟）",
                "config.vmtranslationupdate.option.tipsMinutes");
        translationPackName = config.getString(
                "translationPackName",
                VMTranslationUpdate.MOD_ID,
                "VM汉化组模组汉化包1.12.2",
                "资源包的文件名",
                "config.vmtranslationupdate.option.translationPackName");
        translationPackUrl = config.getString(
                "translationPackUrl",
                VMTranslationUpdate.MOD_ID,
                "https://cdn.modrinth.com/data/IDWIdXwS/versions/",
                "下载资源包的链接", "config.vmtranslationupdate.option.translationPackUrl");
        nameUrl = config.getString(
                "translationPackUrl",
                VMTranslationUpdate.MOD_ID,
                "https://vmct-cn.top/name.json",
                "玩家对应称呼的链接",
                "config.vmtranslationupdate.option.nameUrl");
        if (config.hasChanged()) {
            config.save();
        }
    }
}
