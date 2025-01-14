package top.vmctcn.vmtranslationupdate.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import top.vmctcn.vmtranslationupdate.ModEvents;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.respack.GameOptionsSetter;

public class VMTranslationUpdateClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VMTranslationUpdate.init();

        GameOptionsSetter.init(FabricLoader.getInstance().getGameDir());

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            ModEvents.screenAfterInitEvent(screen);
        });
    }
}
