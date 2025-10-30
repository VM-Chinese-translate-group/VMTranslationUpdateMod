package top.vmctcn.vmtu.mod.neoforge;

import com.mojang.brigadier.Command;
import net.minecraft.server.command.CommandManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.mod.options.GameOptionsSetter;

@Mod(value = VMTranslationUpdate.MOD_ID, dist = Dist.CLIENT)
public class VMTranslationUpdateClientNeoForge {
    public VMTranslationUpdateClientNeoForge(ModContainer modContainer) {
        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            NeoHelper.registerConfigScreen(modContainer, ModConfigHelper::setConfigScreen);

            NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedInEvent.class, event -> {
                if (LanguageHelper.isChineseLanguage()) {
                    ModEvents.playerJoinEvent(event.getEntity());
                }
            });
            NeoForge.EVENT_BUS.addListener(ScreenEvent.Init.Pre.class, event -> {
                if (LanguageHelper.isChineseLanguage()) {
                    ModEvents.screenAfterInitEvent(event.getScreen());
                }
            });

            NeoForge.EVENT_BUS.addListener(RegisterClientCommandsEvent.class, event -> event.getDispatcher().register(
                    CommandManager.literal("vmtu")
                            .then(CommandManager.literal("check")
                                    .executes(context -> {
                                        ModEvents.playerJoinEvent(context.getSource().getPlayer());
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
            ));
        }
    }
}
