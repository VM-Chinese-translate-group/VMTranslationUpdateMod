package top.vmctcn.vmtranslationupdate.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import top.vmctcn.vmtranslationupdate.ModEvents;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;

public class VMTranslationUpdateClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VMTranslationUpdate.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ModConfigUtil.getConfig().displayTips) {
                if (client.player == null) return;
                ModEvents.clientTickEndEvent(client);
            }
        });
    }
}
