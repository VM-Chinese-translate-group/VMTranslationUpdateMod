package top.vmctcn.vmtu.mod.fabric;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ClientModInitializer;
//? if >=1.19.2 {
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
//?} else {
/*import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
*///?}
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;

public class VMTranslationUpdateClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VMTranslationUpdate.init();

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (LanguageHelper.isChineseLanguage()) {
                ModEvents.screenAfterInitEvent(screen);
            }
        });

        ClientPlayConnectionEvents.JOIN.register((handler, packetSender, client) -> {
            if (LanguageHelper.isChineseLanguage()) {
                ModEvents.playerJoinEvent(client.player);
            }
        });

        //? if >=1.19.2 {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                ClientCommandManager.literal("vmtu")
                        .then(ClientCommandManager.literal("check")
                                .executes(context -> {
                                    ModEvents.playerJoinEvent(context.getSource().getPlayer());
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
        ));
        //?} else {
        /*ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("vmtu")
                        .then(ClientCommandManager.literal("check")
                                .executes(context -> {
                                    ModEvents.playerJoinEvent(context.getSource().getPlayer());
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
        );
        *///?}
    }
}