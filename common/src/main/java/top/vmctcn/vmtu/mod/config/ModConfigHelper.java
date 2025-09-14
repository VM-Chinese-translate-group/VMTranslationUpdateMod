package top.vmctcn.vmtu.mod.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.gui.screen.Screen;

public class ModConfigHelper {
    private static ModConfigs modConfigs;

    public static ModConfigs getConfig() {
        if (modConfigs == null) {
            var holder = AutoConfig.register(ModConfigs.class, PartitioningSerializer.wrap(Toml4jConfigSerializer::new));
            modConfigs = holder.getConfig();
        }
        return modConfigs;
    }

    public static Screen setConfigScreen(Screen screen) {
        return AutoConfig.getConfigScreen(ModConfigs.class, screen).get();
    }
}