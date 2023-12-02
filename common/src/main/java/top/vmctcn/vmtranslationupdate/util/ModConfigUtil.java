package top.vmctcn.vmtranslationupdate.util;

import dev.architectury.platform.Platform;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfigs;

public class ModConfigUtil {
    private static ModConfigs modConfigs;

    public static ModConfigs getConfig() {
        if (modConfigs == null) {
            AutoConfig.register(ModConfigs.class, Toml4jConfigSerializer::new);
            modConfigs = AutoConfig.getConfigHolder(ModConfigs.class).getConfig();
        }
        return modConfigs;
    }

    public static void setConfigScreen() {
        Platform.getMod(VMTranslationUpdate.MOD_ID).registerConfigurationScreen(parent -> AutoConfig.getConfigScreen(ModConfigs.class, parent).get());
    }
}