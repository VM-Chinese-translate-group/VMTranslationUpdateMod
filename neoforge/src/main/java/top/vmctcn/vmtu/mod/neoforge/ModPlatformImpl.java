package top.vmctcn.vmtu.mod.neoforge;

import net.neoforged.fml.loading.FMLPaths;
import top.vmctcn.vmtu.multiversion.neoforge.NeoUtils;

import java.nio.file.Path;

public class ModPlatformImpl {
    public static String getGameVersion() {
        return NeoUtils.getVersionInfo().mcVersion();
    }

    public static Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    public static boolean isModLoaded(String modId) {
        return NeoUtils.getLoadingModList().getModFileById(modId) != null;
    }
}
