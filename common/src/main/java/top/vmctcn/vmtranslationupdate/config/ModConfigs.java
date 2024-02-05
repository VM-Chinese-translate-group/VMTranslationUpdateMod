package top.vmctcn.vmtranslationupdate.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

@Config(name = VMTranslationUpdate.MOD_ID)
public class ModConfigs implements ConfigData {
    public boolean autoSwitchLanguage = false;
    public boolean checkModPackTranslationUpdate = true;
    public boolean autoDownloadVMTranslationPack = true;
    public boolean autoInstallVMTranslationPack = true;
    public boolean displayTips = true;
    public boolean playerNameCheck = true;
    //public boolean i18nmodCheck = false;
    public String modPackTranslationUpdateCheckUrl = "https://vmct-cn.top/modpacks/example/update.txt";
    public String modPackTranslationUrl = "https://vmct-cn.top/modpacks/example/";
    public String tipsUrl = "https://vmct-cn.top/tips.txt";
    public String modPackTranslationVersion = "1.0.0";
    public int tipsMinutes = 25;
    public String translationPackName = "VM汉化组模组汉化包1.16";
    public String translationPackUrl= "https://cdn.modrinth.com/data/IDWIdXwS/versions/G2F45m19/";
    public String nameUrl = "https://vmct-cn.top/name.json";
}
