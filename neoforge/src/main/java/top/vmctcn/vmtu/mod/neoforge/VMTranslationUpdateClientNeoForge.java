package top.vmctcn.vmtu.mod.neoforge;

import com.mojang.brigadier.Command;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent.Init.Pre;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;

@Mod(value = VMTranslationUpdate.MOD_ID, dist = Dist.CLIENT)
public class VMTranslationUpdateClientNeoForge {
    public VMTranslationUpdateClientNeoForge(ModContainer modContainer, IEventBus modEventBus) {
        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            NeoHelper.registerConfigScreen(modContainer, ModConfigHelper::setConfigScreen);

            NeoForge.EVENT_BUS.addListener(PlayerLoggedInEvent.class, event -> {
                if (LanguageHelper.isChineseLanguage()) {
                    ModEvents.playerJoinEvent(event.getEntity());
                }
            });

            NeoForge.EVENT_BUS.addListener(Pre.class, event -> {
                if (LanguageHelper.isChineseLanguage()) {
                    ModEvents.screenAfterInitEvent(event.getScreen());
                }
            });

            NeoForge.EVENT_BUS.addListener((RegisterClientCommandsEvent event) -> event.getDispatcher().register(
                    CommandManager.literal("vmtu")
                            .then(CommandManager.literal("check")
                                    .executes(context -> {
                                        if (MinecraftClient.getInstance().player != null) {
                                            ModEvents.playerJoinEvent(MinecraftClient.getInstance().player);
                                        }
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
            ));
        }
    }
}