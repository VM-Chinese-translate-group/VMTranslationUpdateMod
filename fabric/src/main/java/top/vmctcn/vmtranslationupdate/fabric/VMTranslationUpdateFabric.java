package top.vmctcn.vmtranslationupdate.fabric;

import net.fabricmc.api.ModInitializer;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

public class VMTranslationUpdateFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VMTranslationUpdate.init();
    }
}
