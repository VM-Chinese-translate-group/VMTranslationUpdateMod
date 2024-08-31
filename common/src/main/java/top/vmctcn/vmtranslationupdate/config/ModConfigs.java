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
    public boolean i18nUpdateModCheck = true;
    public boolean vaultPatcherCheck = false;

    public String switchLanguage = "zh_cn";
    public String modPackTranslationUpdateCheckUrl = "https://gitee.com/Wulian233/vmtu/raw/main/update/example.txt";
    public String modPackTranslationUrl = "https://vmct-cn.top/modpacks/example/";
    public String tipsUrl = "https://gitee.com/Wulian233/vmtu/raw/main/tips.txt";
    public String modPackTranslationVersion = "1.0.0";
    public int tipsMinutes = 25;
    public String translationPackName = "VM汉化组模组汉化包1.21";
    public String translationPackUrl= "https://cdn.modrinth.com/data/IDWIdXwS/versions/V5YtW17O/";
    public String nameUrl = "https://gitee.com/Wulian233/vmtu/raw/main/name.json";
}