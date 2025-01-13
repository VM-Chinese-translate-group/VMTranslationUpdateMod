package top.vmctcn.vmtranslationupdate.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class ModConfigHelper {
    private static ModConfigs modConfigs;

    public static ModConfigs getConfig() {
        if (modConfigs == null) {
            AutoConfig.register(ModConfigs.class, Toml4jConfigSerializer::new);
            modConfigs = AutoConfig.getConfigHolder(ModConfigs.class).getConfig();
        }
        return modConfigs;
    }
}