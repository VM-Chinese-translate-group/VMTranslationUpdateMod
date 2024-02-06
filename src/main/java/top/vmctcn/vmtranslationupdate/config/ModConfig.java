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
                VMTranslationUpdate.MOD_ID,
                "autoSwitchLanguage",
                false,
                "是否自动根据地区切换语言",
                "config.vmtranslationupdate.option.autoSwitchLanguage");
        checkModPackTranslationUpdate = config.getBoolean(
                VMTranslationUpdate.MOD_ID,
                "checkModPackTranslationUpdate",
                true,
                "是否自动检测整合包汉化版本更新",
                "config.vmtranslationupdate.option.checkModPackTranslationUpdate");
        autoDownloadVMTranslationPack = config.getBoolean(
                VMTranslationUpdate.MOD_ID,
                "autoDownloadVMTranslationPack",
                true,
                "是否自动下载VM汉化资源包",
                "config.vmtranslationupdate.option.autoDownloadVMTranslationPack");
        autoInstallVMTranslationPack = config.getBoolean(
                VMTranslationUpdate.MOD_ID,
                "autoInstallVMTranslationPack",
                true,
                "是否自动安装VM汉化资源包",
                "config.vmtranslationupdate.option.autoInstallVMTranslationPack");
        displayTips = config.getBoolean(
                VMTranslationUpdate.MOD_ID,
                "displayTips",
                true,
                "是否显示知识内容",
                "config.vmtranslationupdate.option.displayTips");
        playerNameCheck = config.getBoolean(
                VMTranslationUpdate.MOD_ID,
                "playerNameCheck",
                true,
                "是否检测玩家名称",
                "config.vmtranslationupdate.option.playerNameCheck");
        modPackTranslationUpdateCheckUrl = config.getString(
                VMTranslationUpdate.MOD_ID,
                "modPackTranslationUpdateCheckUrl",
                "https://vmct-cn.top/rad/update.txt",
                "更新检测链接",
                "config.vmtranslationupdate.option.modPackTranslationUpdateCheckUrl");
        modPackTranslationUrl = config.getString(
                VMTranslationUpdate.MOD_ID,
                "modPackTranslationUrl",
                "https://vmct-cn.top/modpacks/rad/",
                "下载链接",
                "config.vmtranslationupdate.option.modPackTranslationUrl");
        tipsUrl = config.getString(
                VMTranslationUpdate.MOD_ID,
                "tipsUrl",
                "https://vmct-cn.top/tips.txt",
                "获取知识内容的链接",
                "config.vmtranslationupdate.option.tipsUrl");
        modPackTranslationVersion = config.getString(
                VMTranslationUpdate.MOD_ID,
                "modPackTranslationVersion",
                "1.0.0",
                "整合包翻译版本",
                "config.vmtranslationupdate.option.modPackTranslationVersion");
        tipsMinutes = config.getInt(
                VMTranslationUpdate.MOD_ID,
                "tipsMinutes",
                25,
                Integer.MAX_VALUE,
                Integer.MIN_VALUE,
                "发送知识的时间间隔（分钟）",
                "config.vmtranslationupdate.option.tipsMinutes");
        translationPackName = config.getString(
                VMTranslationUpdate.MOD_ID,
                "translationPackName",
                "VM汉化组模组汉化包1.12.2",
                "资源包的文件名",
                "config.vmtranslationupdate.option.translationPackName");
        translationPackUrl = config.getString(
                VMTranslationUpdate.MOD_ID,
                "translationPackUrl",
                "https://cdn.modrinth.com/data/IDWIdXwS/versions/",
                "下载资源包的链接", "config.vmtranslationupdate.option.translationPackUrl");
        nameUrl = config.getString(
                VMTranslationUpdate.MOD_ID,
                "translationPackUrl",
                "https://vmct-cn.top/name.json",
                "玩家对应称呼的链接",
                "config.vmtranslationupdate.option.nameUrl");
        if (config.hasChanged()) {
            config.save();
        }
    }
}
