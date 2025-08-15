package top.vmctcn.vmtu.mod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import top.vmctcn.vmtu.core.util.Reflection;

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
        return Minecraft.getInstance().runDir.toPath();
    }
}
