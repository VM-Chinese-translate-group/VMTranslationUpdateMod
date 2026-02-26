package top.vmctcn.vmtu.mod;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.nio.file.Path;

public class ModPlatform {
    private static Path gameDir;

    public static String getGameVersion() {
        return ForgeVersion.mcVersion;
    }

    public static Path getConfigDir() {
        return Loader.instance().getConfigDir().toPath();
    }

    public static void setGameDir(Path gamePath) {
        gameDir = gamePath;
    }

    public static Path getGameDir() {
        return gameDir;
    }
}
