package top.vmctcn.vmtu.mod.config;

import net.minecraftforge.common.config.Config;
import top.vmctcn.vmtu.core.pack.ResourcePackIndex;
import top.vmctcn.vmtu.mod.ModContexts;

@Config(modid = ModContexts.MOD_ID, category = ModContexts.MOD_ID)
public class ModConfigs {

    @Config.Comment({
            "Miscellaneous settings",
            "杂项设置"
    })
    @Config.Name("misc")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.misc")
    public static final Misc misc = new Misc();

    @Config.Comment({
            "Resource pack settings",
            "资源包设置"
    })
    @Config.Name("resourcePack")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.resourcePack")
    public static final ResourcePack resourcePack = new ResourcePack();

    @Config.Comment({
            "Mod installation check",
            "模组安装检查"
    })
    @Config.Name("modInstallCheck")
    @Config.LangKey("text.autoconfig.vmtranslationupdate.option.modInstallCheck")
    public static final ModInstallCheck modInstallCheck = new ModInstallCheck();

    public static class Misc {
        @Config.Name("devMode")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.misc.devMode")
        public boolean devMode = false;

        @Config.RequiresMcRestart
        @Config.Name("autoSwitchLanguage")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.misc.autoSwitchLanguage")
        public boolean autoSwitchLanguage = true;

        @Config.Name("checkModPackTranslationUpdate")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.misc.checkModPackTranslationUpdate")
        public boolean checkModPackTranslationUpdate = true;
    }

    public static class ResourcePack {
        @Config.RequiresMcRestart
        @Config.Name("autoDownloadVMTranslationPack")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.resourcePack.autoDownloadVMTranslationPack")
        public boolean autoDownloadVMTranslationPack = false;

        @Config.RequiresMcRestart
        @Config.Name("autoLoadExtraTranslationPack")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.resourcePack.autoLoadExtraTranslationPack")
        public boolean autoLoadExtraTranslationPack = false;

        @Config.RequiresMcRestart
        @Config.Comment({
                "Choose translation pack install index",
                "指定汉化资源包的安装顺序"
        })
        @Config.Name("resourcePackIndex")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.resourcePack.resourcePackIndex")
        public ResourcePackIndex resourcePackIndex = ResourcePackIndex.DEFAULT;

        @Config.RequiresMcRestart
        @Config.Comment({
                "Set extra translation pack name (including the file extension)",
                "设置扩展资源包名称（包括后缀名）"
        })
        @Config.Name("extraPackName")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.resourcePack.extraPackName")
        public String extraPackName = "";

        @Config.RequiresMcRestart
        @Config.Comment({
                "To customize the installation index of translation pack, you must set the resource pack sequence to CUSTOM_INDEX for this setting to take effect!",
                "自定义扩展汉化资源包的安装顺序，需要将扩展汉化资源包顺序设置为CUSTOM_INDEX此项才会生效！"
        })
        @Config.Name("extraPackCustomIndex")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.resourcePack.extraPackCustomIndex")
        public int extraPackCustomIndex = 0;
    }

    public static class ModInstallCheck {
        @Config.RequiresMcRestart
        @Config.Name("i18nUpdateModCheck")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.modInstallCheck.i18nUpdateModCheck")
        public boolean i18nUpdateMod = true;

        @Config.RequiresMcRestart
        @Config.Name("vaultPatcherCheck")
        @Config.LangKey("text.autoconfig.vmtranslationupdate.option.modInstallCheck.vaultPatcherCheck")
        public boolean vaultPatcher = false;
    }
}
