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
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.GameOptionsHelper;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.multiversion.neoforge.NeoUtils;

@Mod(value = VMTranslationUpdate.MOD_ID/*? if >=1.20.6 {*/, dist = Dist.CLIENT/*?}*/)
public class VMTranslationUpdateClientNeoForge {
    public VMTranslationUpdateClientNeoForge(IEventBus modEventBus/*? if >=1.20.6 {*/, ModContainer modContainer/*?}*/) {
        //? if 1.20.4 {
        /*ModContainer modContainer = ModList.get().getModContainerById(VMTranslationUpdate.MOD_ID).orElseThrow();
        NeoUtils.getClientModIgnoredServerOnly(modContainer);
        *///?}

        if (NeoUtils.getDist().isClient()) {
            VMTranslationUpdate.init();

            NeoUtils.registerConfigScreen(modContainer, ModConfigHelper::setConfigScreen);

            NeoForge.EVENT_BUS.addListener(PlayerLoggedInEvent.class, event -> {
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
                    Commands.literal("vmtu")
                            .then(Commands.literal("check")
                                    .executes(context -> {
                                        ModEvents.playerJoinEvent(context.getSource().getPlayer());
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
            ));

            modEventBus.addListener(FMLConstructModEvent.class, event -> GameOptionsHelper.autoDownloadAndLoadPack());
        }
    }
}
