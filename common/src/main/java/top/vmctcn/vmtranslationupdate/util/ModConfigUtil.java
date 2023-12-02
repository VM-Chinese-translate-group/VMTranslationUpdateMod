package top.vmctcn.vmtranslationupdate.util;

import dev.architectury.platform.Platform;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfig;

public class ModConfigUtil {
    private static ModConfig configScreen;

    public static ModConfig getConfig() {
        if (configScreen == null) {
            AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
            configScreen = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        }
        return configScreen;
    }

    public static void setConfigScreen() {
        Platform.getMod(VMTranslationUpdate.MOD_ID).registerConfigurationScreen(parent -> AutoConfig.getConfigScreen(ModConfig.class, parent).get());
    }
}