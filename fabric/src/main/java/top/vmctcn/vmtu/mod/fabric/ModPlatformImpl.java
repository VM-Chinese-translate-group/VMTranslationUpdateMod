package top.vmctcn.vmtu.mod.fabric;

import net.fabricmc.loader.api.FabricLoader;
import top.vmctcn.vmtu.core.util.Reflection;

import java.nio.file.Path;

public class ModPlatformImpl {
    public static String getGameVersion() {
        try {
            // Fabric
            return (String) Reflection.clazz("net.fabricmc.loader.impl.FabricLoaderImpl")
                    .get("INSTANCE")
                    .get("getGameProvider()")
                    .get("getNormalizedGameVersion()").get();
        } catch (Exception ignored) {

        }
        try {
            // Quilt
            return (String) Reflection.clazz("org.quiltmc.loader.impl.QuiltLoaderImpl")
                    .get("INSTANCE")
                    .get("getGameProvider()")
                    .get("getNormalizedGameVersion()").get();
        } catch (Exception ignored) {

        }
        return null;
    }

    public static Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
