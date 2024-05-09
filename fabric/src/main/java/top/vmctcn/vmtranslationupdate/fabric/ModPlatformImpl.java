package top.vmctcn.vmtranslationupdate.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class ModPlatformImpl {
    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}
