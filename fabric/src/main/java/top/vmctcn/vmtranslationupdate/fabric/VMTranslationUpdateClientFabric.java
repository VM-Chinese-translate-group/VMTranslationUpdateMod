package top.vmctcn.vmtranslationupdate.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.util.ScreenUtil;

public class VMTranslationUpdateClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VMTranslationUpdate.init();

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            ScreenUtil.screenAfterInitEvent(screen);
        });
    }
}