package top.vmctcn.vmtranslationupdate.fabric;

import net.fabricmc.api.ClientModInitializer;
import top.vmctcn.vmtranslationupdate.util.ConfigUtil;

public class VMTranslationUpdateModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigUtil.setConfigScreen();
    }
}
