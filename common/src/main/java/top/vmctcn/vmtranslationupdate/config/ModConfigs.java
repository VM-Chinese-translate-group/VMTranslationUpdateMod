package top.vmctcn.vmtranslationupdate.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

@Config(name = VMTranslationUpdate.MOD_ID)
public class ModConfigs implements ConfigData {
    public boolean autoSwitchLanguage = false;
    public boolean checkModPackTranslationUpdate = false;
    public boolean autoDownloadVMTranslationPack = false;
    public boolean autoInstallVMTranslationPack = false;
    public boolean displayTips = false;
    public boolean playerNameCheck = false;
    public String modPackTranslationUpdateCheckUrl = "https://vmct-cn.top/example/update.txt";
    public String modPackTranslationUrl = "https://vmct-cn.top/example/";
    public String tipsUrl = "https://vmct-cn.top/tips.txt";
    public String modPackTranslationVersion = "1.0.0";
    public int tipsMinutes = 25;
    public String traslationPackName = "VM汉化组模组汉化包1.20";
    public String traslationPackUrl= "https://cdn-raw.modrinth.com/data/IDWIdXwS/versions/V5YtW17O/";
    public String nameUrl = "https://vmct-cn.top/name.json";
}
