package top.vmctcn.vmtu.mod.config;

import net.minecraftforge.common.config.Config;
import top.vmctcn.vmtu.core.pack.PackSource;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

@Config(modid = VMTranslationUpdate.MOD_ID, category = VMTranslationUpdate.MOD_ID)
public class ModConfigs {

    @Config.Name("testMode")
    @Config.LangKey("config.vmtranslationupdate.option.testMode")
    public static boolean testMode = false;

    @Config.Name("autoSwitchLanguage")
    @Config.LangKey("config.vmtranslationupdate.option.autoSwitchLanguage")
    public static boolean autoSwitchLanguage = false;

    @Config.Name("autoDownloadVMTranslationPack")
    @Config.LangKey("config.vmtranslationupdate.option.autoDownloadVMTranslationPack")
    public static boolean autoDownloadVMTranslationPack = true;

    @Config.Name("translationPackSource")
    @Config.LangKey("config.vmtranslationupdate.option.translationPackSource")
    @Config.Comment("Choose translation pack download source")
    public static PackSource translationPackSource = PackSource.GITEE;

    @Config.Name("checkModPackTranslationUpdate")
    @Config.LangKey("config.vmtranslationupdate.option.checkModPackTranslationUpdate")
    public static boolean checkModPackTranslationUpdate = true;

    @Config.Name("i18nUpdateModCheck")
    @Config.LangKey("config.vmtranslationupdate.option.i18nUpdateModCheck")
    public static boolean i18nUpdateModCheck = true;

    @Config.Name("vaultPatcherCheck")
    @Config.LangKey("config.vmtranslationupdate.option.vaultPatcherCheck")
    public static boolean vaultPatcherCheck = false;
}
