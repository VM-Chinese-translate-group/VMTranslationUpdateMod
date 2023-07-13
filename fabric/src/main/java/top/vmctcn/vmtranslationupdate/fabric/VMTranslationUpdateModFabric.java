package top.vmctcn.vmtranslationupdate.fabric;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdateMod;
import net.fabricmc.api.ModInitializer;

public class VMTranslationUpdateModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VMTranslationUpdateMod.init();
    }
}
