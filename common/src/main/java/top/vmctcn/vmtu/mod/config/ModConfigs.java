package top.vmctcn.vmtu.mod.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import top.vmctcn.vmtu.core.pack.ExtraPackIndex;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

@Config(name = VMTranslationUpdate.MOD_ID)
public class ModConfigs extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("misc")
    @ConfigEntry.Gui.TransitiveObject
    public Misc misc = new Misc();

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("extraTranslationPack")
    @ConfigEntry.Gui.TransitiveObject
    public ExtraTranslationPack extraTranslationPack = new ExtraTranslationPack();

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("requireModCheck")
    @ConfigEntry.Gui.TransitiveObject
    public RequireModCheck requireModCheck = new RequireModCheck();

    @Config(name = "misc")
    public static class Misc implements ConfigData {
        public boolean testMode = false;

        @ConfigEntry.Gui.RequiresRestart
        public boolean autoSwitchLanguage = true;

        @ConfigEntry.Gui.RequiresRestart
        public boolean autoDownloadVMTranslationPack = false;

        public boolean checkModPackTranslationUpdate = true;
    }

    @Config(name = "extraTranslationPack")
    public static class ExtraTranslationPack implements ConfigData {
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public String packName = "";

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public ExtraPackIndex index = ExtraPackIndex.BOTTOM_OF_CFPA;

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public int customIndex = 0;
    }

    @Config(name = "requireModCheck")
    public static class RequireModCheck implements ConfigData {
        @ConfigEntry.Gui.RequiresRestart
        public boolean i18nUpdateMod = true;

        @ConfigEntry.Gui.RequiresRestart
        public boolean vaultPatcher = false;
    }
}
