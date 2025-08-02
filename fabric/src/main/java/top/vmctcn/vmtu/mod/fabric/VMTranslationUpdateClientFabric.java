package top.vmctcn.vmtu.mod.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import top.vmctcn.vmtu.mod.command.ModCommands;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.options.GameOptionsSetter;

public class VMTranslationUpdateClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VMTranslationUpdate.init();

        GameOptionsSetter.init(FabricLoader.getInstance().getGameDir());

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            ModCommands.VMTUCommand();
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            ModEvents.screenAfterInitEvent(screen);
        });

        ClientPlayConnectionEvents.JOIN.register((handler, packetSender, client) -> {
            ModEvents.playerJoinEvent();
        });
    }
}