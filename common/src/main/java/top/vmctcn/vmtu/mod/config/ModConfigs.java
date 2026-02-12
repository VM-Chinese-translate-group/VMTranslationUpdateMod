package top.vmctcn.vmtu.mod.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import top.vmctcn.vmtu.core.pack.ResourcePackIndex;
import top.vmctcn.vmtu.mod.ModContexts;

@Config(name = ModContexts.MOD_ID)
public class ModConfigs implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public Misc misc = new Misc();

    @ConfigEntry.Gui.CollapsibleObject
    public ResourcePack resourcePack = new ResourcePack();

    @ConfigEntry.Gui.CollapsibleObject
    public ModInstallCheck modInstallCheck = new ModInstallCheck();

    public static class Misc {
        public boolean devMode = false;

        @ConfigEntry.Gui.RequiresRestart
        public boolean autoSwitchLanguage = false;

        public boolean checkModPackTranslationUpdate = false;
    }

    public static class ResourcePack {
        @ConfigEntry.Gui.RequiresRestart
        public boolean autoDownloadVMTranslationPack = true;

        @ConfigEntry.Gui.RequiresRestart
        public boolean autoLoadExtraTranslationPack = false;

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public ResourcePackIndex resourcePackIndex = ResourcePackIndex.DEFAULT;

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public String extraPackName = "";

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public int extraPackCustomIndex = 0;
    }

    public static class ModInstallCheck {
        @ConfigEntry.Gui.RequiresRestart
        public boolean i18nUpdateMod = true;

        @ConfigEntry.Gui.RequiresRestart
        public boolean vaultPatcher = false;

        @ConfigEntry.Gui.RequiresRestart
        public boolean textureLocaleRedirector = false;
    }
}