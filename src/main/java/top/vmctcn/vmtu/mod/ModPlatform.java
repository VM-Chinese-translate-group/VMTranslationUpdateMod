package top.vmctcn.vmtu.mod;

import net.minecraftforge.fml.common.Loader;
import top.vmctcn.vmtu.core.util.Reflection;

import java.io.File;
import java.nio.file.Path;

public class ModPlatform {
    public static String getGameVersion() {
        try {
            // https://github.com/MinecraftForge/MinecraftForge/blob/1.12.x/src/main/java/net/minecraftforge/common/ForgeVersion.java#L64
            return (String) Reflection.clazz("net.minecraftforge.common.ForgeVersion").get("mcVersion").get();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static Path getConfigDir() {
        return Loader.instance().getConfigDir().toPath();
    }

    public static Path getGameDir() {
        try {
            // https://github.com/MinecraftForge/MinecraftForge/blob/d3f01843f7e7a4f613b5e8113d381fd8747b4343/src/main/java/net/minecraftforge/fml/common/Loader.java#L177
            return ((File) Reflection.clazz("net.minecraftforge.fml.common.Loader").get("minecraftDir").get()).toPath();
        } catch (Exception ignored) {
        }
        return null;
    }
}
