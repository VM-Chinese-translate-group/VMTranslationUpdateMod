package top.vmctcn.vmtranslationupdate.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

@Config(name = VMTranslationUpdate.MOD_ID)
public class ModConfigs implements ConfigData {
    public boolean autoSwitchLanguage = true;
    public String switchLanguage = "zh_cn";

    public boolean checkModPackTranslationUpdate = true;
    public String modPackTranslationUpdateCheckUrl = "https://gitee.com/Wulian233/vmtu/raw/main/update/example.txt";
    public String modPackTranslationUrl = "https://vmct-cn.top/modpacks/example/";

    public String modPackTranslationVersion = "1.0.0";
    public boolean displayTips = true;
    public String tipsUrl = "https://gitee.com/Wulian233/vmtu/raw/main/tips.txt";

    @ConfigEntry.BoundedDiscrete(min = 1, max = 120)
    public int tipsMinutes = 25;

    public boolean i18nUpdateModCheck = true;
    public boolean vaultPatcherCheck = false;
}