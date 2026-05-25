package top.vmctcn.vmtu.mod.forge;

import com.mojang.brigadier.Command;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
//? if >=1.18.2 {
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.ScreenEvent;
//?} else {
/*import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
*///?}
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import top.vmctcn.vmtu.libraries.common.CommonContexts;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdateMod;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;
import top.vmctcn.vmtu.multiversion.forge.ForgeUtils;

import java.nio.file.Path;

@Mod(ModContexts.MOD_ID)
public class VMTranslationUpdateModClientForge {
    public VMTranslationUpdateModClientForge() {
        @SuppressWarnings("removal")
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ForgeUtils.getClientModIgnoredServerOnly(ModContexts.MOD_ID);

        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdateMod.init();

            ForgeUtils.registerConfigScreen(ModContexts.MOD_ID, ModConfigHelper::setConfigScreen);

            MinecraftForge.EVENT_BUS.<PlayerEvent.PlayerLoggedInEvent>addListener(event -> {
                if (LanguageUtils.isChineseLanguage()) {
                    ModEvents.playerJoinEvent(/*? if >=1.19.2 {*//*event.getEntity()*//*?} else {*/event.getPlayer()/*?}*/);
                }
            });
            MinecraftForge.EVENT_BUS.</*? if >=1.19.2 {*//*ScreenEvent.Init.Pre*//*?} else if 1.18.2 {*/ScreenEvent.InitScreenEvent.Pre/*?} else {*//*GuiScreenEvent.InitGuiEvent.Pre*//*?}*/>addListener(event -> {
                if (LanguageUtils.isChineseLanguage()) {
                    ModEvents.screenAfterInitEvent(/*? if >=1.18.2 {*/event.getScreen()/*?} else {*//*event.getGui()*//*?}*/);
                }
            });

            MinecraftForge.EVENT_BUS.</*? if >=1.18.2 {*/RegisterClientCommandsEvent/*?} else {*//*RegisterCommandsEvent*//*?}*/>addListener(event -> {
                event.getDispatcher().register(
                        Commands.literal("vmtu")
                                .then(Commands.literal("check").executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    ModEvents.checkTranslationUpdateCommand(/*? if >=1.19.2 {*//*source.getPlayer()*//*?} else {*/source.getPlayerOrException()/*?}*/);
                                    ModEvents.checkModpackUpdateCommand(/*? if >=1.19.2 {*//*source.getPlayer()*//*?} else {*/source.getPlayerOrException()/*?}*/);
                                    return Command.SINGLE_SUCCESS;
                                }).then(Commands.literal("modpack").executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    ModEvents.checkModpackUpdateCommand(/*? if >=1.19.2 {*//*source.getPlayer()*//*?} else {*/source.getPlayerOrException()/*?}*/);
                                    return Command.SINGLE_SUCCESS;
                                })).then(Commands.literal("translation").executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    ModEvents.checkTranslationUpdateCommand(/*? if >=1.19.2 {*//*source.getPlayer()*//*?} else {*/source.getPlayerOrException()/*?}*/);
                                    return Command.SINGLE_SUCCESS;
                                })))
                );
            });

            modEventBus.<FMLConstructModEvent>addListener(event -> {
                String gameVersion = ForgeUtils.getVersionInfo().mcVersion();
                Path gameDir = FMLPaths.GAMEDIR.get();
                CommonContexts.setGameInfo(gameVersion, gameDir);

                VMTranslationUpdateMod.autoDownloadAndLoadPack();
            });
        }
    }
}
