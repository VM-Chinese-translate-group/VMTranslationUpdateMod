package top.vmctcn.vmtranslationupdate.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

@Config(name = VMTranslationUpdate.MOD_ID)
public class ConfigScreen implements ConfigData {
    public String updateUrl = "https://vmct-cn.top/modpacks/wolf/update.txt";
    public String downloadUrl = "https://vmct-cn.top/modpacks/wolf/";
    public  String tipsUrl = "https://vmct-cn.top/tips.txt";
    public  String translationVersion = "1.0.0";
    public  Integer minutes = 25;
    public  String packName = "VM汉化组模组汉化包1.20.zip";
    public  String packUrl= "https://cdn.modrinth.com/data/IDWIdXwS/versions/V5YtW17O/VM%E6%B1%89%E5%8C%96%E7%BB%84%E6%A8%A1%E7%BB%84%E6%B1%89%E5%8C%96%E5%8C%851.20.zip";
}
