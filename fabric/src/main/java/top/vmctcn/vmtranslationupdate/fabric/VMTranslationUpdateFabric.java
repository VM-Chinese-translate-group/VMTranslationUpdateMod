package top.vmctcn.vmtranslationupdate.fabric;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import net.fabricmc.api.ModInitializer;

public class VMTranslationUpdateFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VMTranslationUpdate.init();
    }
}
