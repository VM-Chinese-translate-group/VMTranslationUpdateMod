package top.vmctcn.vmtu.mod.fabric;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.gameoptions.GameOptionsSetter;

public class VMTranslationUpdateClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VMTranslationUpdate.init();

        GameOptionsSetter.init(FabricLoader.getInstance().getGameDir());

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> ModEvents.screenAfterInitEvent(screen));

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> ModEvents.playerJoinEvent(client.player));

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                ClientCommandManager.literal("vmtu")
                        .then(ClientCommandManager.literal("check")
                                .executes(context -> {
                                    ModEvents.playerJoinEvent(context.getSource().getPlayer());
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
        ));
    }
}
