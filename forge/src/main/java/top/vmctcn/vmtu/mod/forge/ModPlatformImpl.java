package top.vmctcn.vmtu.mod.forge;

import com.google.auto.service.AutoService;
import cpw.mods.modlauncher.Launcher;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.util.Reflection;
import top.vmctcn.vmtu.multiversion.forge.ForgeUtils;
import top.vmctcn.vmtu.mod.ModPlatform;

import java.nio.file.Path;

@AutoService(ModPlatform.class)
public class ModPlatformImpl implements ModPlatform {
    @Override
    public String getGameVersion() {
        //? if >=1.18.2 {
        /*return FMLLoader.versionInfo().mcVersion();
        *///?} else {
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
        //?}
    }

    @Override
    public Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FMLLoader.getLoadingModList().getModFileById(modId) != null;
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return ForgeUtils.isDevelopmentEnvironment();
    }
}
