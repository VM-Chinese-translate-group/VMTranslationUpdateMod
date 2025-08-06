package top.vmctcn.vmtu.mod.fabric;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.options.GameOptionsSetter;

public class VMTranslationUpdateClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VMTranslationUpdate.init();

        GameOptionsSetter.init(FabricLoader.getInstance().getGameDir());

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (VMTranslationUpdate.isChineseLanguage()) {
                ModEvents.screenAfterInitEvent(screen);
            }
        });

        ClientPlayConnectionEvents.JOIN.register((handler, packetSender, client) -> {
            if (VMTranslationUpdate.isChineseLanguage()) {
                ModEvents.playerJoinEvent(client.player);
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                LiteralArgumentBuilder.<FabricClientCommandSource>literal("vmtu")
                        .then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("check")
                                .executes(context -> {
                                    ModEvents.playerJoinEvent(MinecraftClient.getInstance().player);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
        ));
    }
}