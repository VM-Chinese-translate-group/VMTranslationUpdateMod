package top.vmctcn.vmtranslationupdate;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class ModPlatform {
    @ExpectPlatform
    public static boolean isModLoaded(String modid) {
        throw new AssertionError();
    }
}
