package top.vmctcn.vmtu.mod.neoforge;

import com.mojang.brigadier.Command;
import net.minecraft.commands.Commands;
//? if >=1.20.6 {
import net.neoforged.api.distmarker.Dist;
//?}
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
//? if 1.20.4 {
/*import net.neoforged.fml.ModList;
*///?}
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;
import top.vmctcn.vmtu.multiversion.neoforge.NeoUtils;

@Mod(value = ModContexts.MOD_ID/*? if >=1.20.6 {*/, dist = Dist.CLIENT/*?}*/)
public class VMTranslationUpdateClientNeoForge {
    public VMTranslationUpdateClientNeoForge(IEventBus modEventBus/*? if >=1.20.6 {*/, ModContainer modContainer/*?}*/) {
        //? if 1.20.4 {
        /*ModContainer modContainer = ModList.get().getModContainerById(ModContexts.MOD_ID).orElseThrow();
        NeoUtils.getClientModIgnoredServerOnly(modContainer);
        *///?}

        if (NeoUtils.getDist().isClient()) {
            VMTranslationUpdate.init();

            NeoUtils.registerConfigScreen(modContainer, ModConfigHelper::setConfigScreen);

            NeoForge.EVENT_BUS.addListener(PlayerLoggedInEvent.class, event -> {
                if (LanguageUtils.isChineseLanguage()) {
                    ModEvents.playerJoinEvent(event.getEntity());
                }
            });
            NeoForge.EVENT_BUS.addListener(ScreenEvent.Init.Pre.class, event -> {
                if (LanguageUtils.isChineseLanguage()) {
                    ModEvents.screenAfterInitEvent(event.getScreen());
                }
            });

            NeoForge.EVENT_BUS.addListener(RegisterClientCommandsEvent.class, event -> event.getDispatcher().register(
                    Commands.literal("vmtu")
                            .then(Commands.literal("check")
                                    .executes(context -> {
                                        ModEvents.checkModpackUpdateCommand(context.getSource().getPlayer());
                                        ModEvents.checkTranslationUpdateCommand(context.getSource().getPlayer());
                                        return Command.SINGLE_SUCCESS;
                                    })
                                    .then(Commands.literal("modpack"))
                                    .executes(context -> {
                                        ModEvents.checkModpackUpdateCommand(context.getSource().getPlayer());
                                        return Command.SINGLE_SUCCESS;
                                    })
                                    .then(Commands.literal("translation"))
                                    .executes(context -> {
                                        ModEvents.checkTranslationUpdateCommand(context.getSource().getPlayer());
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
            ));

            modEventBus.addListener(FMLConstructModEvent.class, event -> VMTranslationUpdate.autoDownloadAndLoadPack());
        }
    }
}
