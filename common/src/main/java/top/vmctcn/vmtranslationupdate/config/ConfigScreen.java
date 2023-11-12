package top.vmctcn.vmtranslationupdate.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

@Config(name = VMTranslationUpdate.MOD_ID)
public class ConfigScreen implements ConfigData {
    public boolean autoSwitchLanguage = false;
    public String updateUrl = "https://vmct-cn.top/sb3/update.txt";
    public String downloadUrl = "https://vmct-cn.top/sb3/";
    public String tipsUrl = "https://vmct-cn.top/tips.txt";
    public String translationVersion = "1.0.0";
    public int minutes = 25;
    public String packName = "VM汉化组模组汉化包1.18";
    public String packUrl= "https://cdn.modrinth.com/data/IDWIdXwS/versions/xCpjJgHS/VM%E6%B1%89%E5%8C%96%E7%BB%84%E6%A8%A1%E7%BB%84%E6%B1%89%E5%8C%96%E5%8C%851.18";
    public String nameUrl = "https://vmct-cn.top/name.json";
}
