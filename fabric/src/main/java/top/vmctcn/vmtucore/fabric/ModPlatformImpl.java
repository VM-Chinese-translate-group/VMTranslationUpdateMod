package top.vmctcn.vmtucore.fabric;

import com.google.auto.service.AutoService;
import top.vmctcn.vmtucore.ModPlatform;
import top.vmctcn.vmtucore.util.ReflectionHelper;

@AutoService(ModPlatform.class)
public class ModPlatformImpl implements ModPlatform {
    @Override
    public String getGameVersion() {
        try {
            // Fabric
            return (String) ReflectionHelper.clazz("net.fabricmc.loader.impl.FabricLoaderImpl")
                    .get("INSTANCE")
                    .get("getGameProvider()")
                    .get("getNormalizedGameVersion()").get();
        } catch (Exception ignored) {

        }
        try {
            // Quilt
            return (String) ReflectionHelper.clazz("org.quiltmc.loader.impl.QuiltLoaderImpl")
                    .get("INSTANCE")
                    .get("getGameProvider()")
                    .get("getNormalizedGameVersion()").get();
        } catch (Exception ignored) {

        }
        return null;
    }
}
