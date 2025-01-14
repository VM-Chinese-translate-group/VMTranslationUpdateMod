package top.vmctcn.vmtranslationupdate.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.respack.ResPackSource;

@Config(name = VMTranslationUpdate.MOD_ID)
public class ModConfigs implements ConfigData {
    public boolean testMode = false;

    public boolean autoSwitchLanguage = true;

    public boolean autoDownloadVMTranslationPack = false;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public ResPackSource translationPackSource = ResPackSource.GITEE;

    public boolean checkModPackTranslationUpdate = true;

    public boolean i18nUpdateModCheck = true;
    public boolean vaultPatcherCheck = false;
}
