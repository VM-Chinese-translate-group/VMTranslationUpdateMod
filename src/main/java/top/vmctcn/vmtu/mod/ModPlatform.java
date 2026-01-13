package top.vmctcn.vmtu.mod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Loader;

import java.nio.file.Path;

public class ModPlatform {
    public static String getGameVersion() {
        return ForgeVersion.mcVersion;
    }

    public static Path getConfigDir() {
        return Loader.instance().getConfigDir().toPath();
    }

    public static Path getGameDir() {
        return Minecraft.getInstance().gameDir.toPath();
    }
}
