package top.vmctcn.vmtu.multiversion.fabric;

import net.fabricmc.loader.api.FabricLoader;
import top.vmctcn.vmtu.libraries.resourcepack.util.Reflection;

public class FabricUtils {
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
        return FabricLoader.getInstance().getModContainer("minecraft").get().getMetadata().getVersion().getFriendlyString();
    }
}
