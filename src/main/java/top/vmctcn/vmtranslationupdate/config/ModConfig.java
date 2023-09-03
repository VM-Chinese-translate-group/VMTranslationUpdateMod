package top.vmctcn.vmtranslationupdate.config;

import net.minecraftforge.common.config.Configuration;

public class ModConfig {
    public static Configuration config;
    public static int minutes;
    public static String updateUrl,downloadUrl,tipsUrl,translationVersion,packName,packUrl;

    public static void syncConfig() {
        updateUrl = config.get("VM汉化组汉化检测配置", "updateUrl", "https://vmct-cn.top/rad/update.txt", "获取TXT检测更新的url").getString();
        downloadUrl = config.get("VM汉化组汉化检测配置", "downloadUrl", "https://vmct-cn.top/rad/", "提示玩家下载地址的url").getString();
        tipsUrl = config.get("VM汉化组汉化检测配置", "tipsUrl", "https://vmct-cn.top/tips.txt", "获取知识内容的url").getString();
        translationVersion = config.get("VM汉化组汉化检测配置", "translationVersion", "第一版", "当前汉化版本").getString();
        minutes = config.get("VM汉化组汉化检测配置", "minutes", 25, "发送知识的时间间隔（分钟）").getInt();
        packName = config.get("VM汉化组汉化检测配置", "packName", "", "资源包的文件名").getString();
        packUrl = config.get("VM汉化组汉化检测配置", "packUrl", "", "下载资源包的链接").getString();
        if (config.hasChanged()) {
            config.save();
        }
    }
}
