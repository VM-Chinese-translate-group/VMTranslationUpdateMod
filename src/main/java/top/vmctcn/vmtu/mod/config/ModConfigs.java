package top.vmctcn.vmtu.mod.config;

import net.minecraftforge.common.config.Config;
import top.vmctcn.vmtu.core.pack.ResourcePackIndex;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

@Config(modid = VMTranslationUpdate.MOD_ID, category = VMTranslationUpdate.MOD_ID)
public class ModConfigs {

    @Config.Name("devMode")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.devMode")
    public static boolean devMode = false;

    @Config.RequiresMcRestart
    @Config.Name("autoSwitchLanguage")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.autoSwitchLanguage")
    public static boolean autoSwitchLanguage = true;

    @Config.RequiresMcRestart
    @Config.Name("autoDownloadVMTranslationPack")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.autoDownloadVMTranslationPack")
    public static boolean autoDownloadVMTranslationPack = false;

    @Config.Name("checkModPackTranslationUpdate")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.checkModPackTranslationUpdate")
    public static boolean checkModPackTranslationUpdate = true;

    @Config.RequiresMcRestart
    @Config.Name("autoLoadExtraTranslationPack")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.autoLoadExtraTranslationPack")
    public static boolean autoLoadExtraTranslationPack = false;

    @Config.RequiresMcRestart
    @Config.Comment({
            "Choose translation pack install index",
            "指定汉化资源包的安装顺序"
    })
    @Config.Name("resourcePackIndex")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.resourcePackIndex")
    public static ResourcePackIndex resourcePackIndex = ResourcePackIndex.DEFAULT;

    @Config.RequiresMcRestart
    @Config.Comment({
            "Set extra translation pack name (including the file extension)",
            "设置扩展资源包名称（包括后缀名）"
    })
    @Config.Name("extraPackName")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.extraPackName")
    public static String extraPackName = "";

    @Config.RequiresMcRestart
    @Config.Comment({
            "To customize the installation index of translation pack, you must set the resource pack sequence to CUSTOM_INDEX for this setting to take effect!",
            "自定义扩展汉化资源包的安装顺序，需要将扩展汉化资源包顺序设置为CUSTOM_INDEX此项才会生效！"
    })
    @Config.Name("extraPackCustomIndex")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.extraPackCustomIndex")
    public static int extraPackCustomIndex = 0;

    @Config.RequiresMcRestart
    @Config.Name("i18nUpdateModCheck")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.i18nUpdateModCheck")
    public static boolean i18nUpdateModCheck = true;

    @Config.RequiresMcRestart
    @Config.Name("vaultPatcherCheck")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.vaultPatcherCheck")
    public static boolean vaultPatcherCheck = false;
}
