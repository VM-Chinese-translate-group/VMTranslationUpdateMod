package top.vmctcn.vmtu.mod.forge;

import cpw.mods.modlauncher.Launcher;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.util.Reflection;
import top.vmctcn.vmtu.multiversion.forge.ForgeUtils;

import java.nio.file.Path;

public class ModPlatformImpl {
    public static String getGameVersion() {
        //? if >=1.18.2 {
        return FMLLoader.versionInfo().mcVersion();
        //?} else {
        /*try {
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
        *///?}
    }

    public static Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    public static boolean isModLoaded(String modId) {
        return FMLLoader.getLoadingModList().getModFileById(modId) != null;
    }

    public static boolean isDevelopmentEnvironment() {
        return ForgeUtils.isDevelopmentEnvironment();
    }
}
