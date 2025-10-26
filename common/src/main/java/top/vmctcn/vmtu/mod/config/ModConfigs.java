package top.vmctcn.vmtu.mod.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import top.vmctcn.vmtu.core.pack.ExtraPackIndex;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

@Config(name = VMTranslationUpdate.MOD_ID)
public class ModConfigs implements ConfigData {

    public boolean devMode = false;

    @ConfigEntry.Gui.RequiresRestart
    public boolean autoSwitchLanguage = true;

    @ConfigEntry.Gui.RequiresRestart
    public boolean autoDownloadVMTranslationPack = false;

    public boolean checkModPackTranslationUpdate = true;

    @ConfigEntry.Gui.RequiresRestart
    public boolean autoLoadExtraTranslationPack = false;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public String extraPackName = "";

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public ExtraPackIndex extraPackIndex = ExtraPackIndex.TOP_OF_CFPA;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public int extraPackCustomIndex = 0;

    @ConfigEntry.Gui.RequiresRestart
    public boolean i18nUpdateModCheck = true;

    @ConfigEntry.Gui.RequiresRestart
    public boolean vaultPatcherCheck = false;
}
