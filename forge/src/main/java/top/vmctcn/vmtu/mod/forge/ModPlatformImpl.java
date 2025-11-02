package top.vmctcn.vmtu.mod.forge;

import com.google.auto.service.AutoService;
import cpw.mods.modlauncher.Launcher;
import net.minecraftforge.fml.loading.FMLPaths;
import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.util.Reflection;
import top.vmctcn.vmtu.mod.ModPlatform;

import java.nio.file.Path;

@AutoService(ModPlatform.class)
public class ModPlatformImpl implements ModPlatform {
    @Override
    public String getGameVersion() {
        // MinecraftForge 1.13~1.20.2
        // NeoForge 1.20.1~
        try {
            String[] args = (String[]) Reflection.clazz(Launcher.INSTANCE).get("argumentHandler").get("args").get();
            for (int i = 0; i < args.length - 1; ++i) {
                if (args[i].equalsIgnoreCase("--fml.mcversion")) {
                    return args[i + 1];
                }
            }
        } catch (Exception e) {
            VMTUCore.LOGGER.warn("Error getting minecraft version: %s", e);
        }
        return null;
    }

    @Override
    public Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }
}
