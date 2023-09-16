package top.vmctcn.vmtranslationupdate.config;

import net.minecraftforge.common.config.Configuration;

public class ModConfig {
    public static Configuration config;
    public static int minutes;
    public static boolean autoSwitchLanguage;
    public static String updateUrl,downloadUrl,tipsUrl,translationVersion,packName,packUrl;

    public static void syncConfig() {
        autoSwitchLanguage = config.get("VM汉化更新检测配置", "autoSwitchLanguage", false, "自动根据地区切换语言").getBoolean();
        updateUrl = config.get("VM汉化更新检测配置", "updateUrl", "https://vmct-cn.top/rad/update.txt", "更新检测链接").getString();
        downloadUrl = config.get("VM汉化更新检测配置", "downloadUrl", "https://vmct-cn.top/modpacks/rad/", "下载链接").getString();
        tipsUrl = config.get("VM汉化更新检测配置", "tipsUrl", "https://vmct-cn.top/tips.txt", "获取知识内容的链接").getString();
        translationVersion = config.get("VM汉化更新检测配置", "translationVersion", "1.0.0", "当前汉化版本").getString();
        minutes = config.get("VM汉化更新检测配置", "minutes", 25, "发送知识的时间间隔（分钟）").getInt();
        packName = config.get("VM汉化更新检测配置", "packName", "VM汉化组模组汉化包1.12.2", "资源包的文件名").getString();
        packUrl = config.get("VM汉化更新检测配置", "packUrl", "https://cdn.modrinth.com/data/IDWIdXwS/versions/zdk8GqtV/VM%E6%B1%89%E5%8C%96%E7%BB%84%E6%A8%A1%E7%BB%84%E6%B1%89%E5%8C%96%E5%8C%851.12.2", "下载资源包的链接").getString();
        if (config.hasChanged()) {
            config.save();
        }
    }
}
