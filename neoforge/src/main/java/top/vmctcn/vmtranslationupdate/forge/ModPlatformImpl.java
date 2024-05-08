package top.vmctcn.vmtranslationupdate.forge;

import net.neoforged.fml.ModList;

public class ModPlatformImpl {
    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
}
