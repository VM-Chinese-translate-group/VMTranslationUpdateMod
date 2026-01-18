package top.vmctcn.vmtu.mod.forge;

import com.mojang.brigadier.Command;
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
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;
import top.vmctcn.vmtu.multiversion.forge.ForgeUtils;

@Mod(ModContexts.MOD_ID)
public class VMTranslationUpdateClientForge {
    public VMTranslationUpdateClientForge() {
        @SuppressWarnings("removal")
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ForgeUtils.getClientModIgnoredServerOnly(ModContexts.MOD_ID);

        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            ForgeUtils.registerConfigScreen(ModContexts.MOD_ID, ModConfigHelper::setConfigScreen);

            MinecraftForge.EVENT_BUS.<PlayerEvent.PlayerLoggedInEvent>addListener(event -> {
                if (LanguageUtils.isChineseLanguage()) {
                    //? if >=1.19.2 {
                    ModEvents.playerJoinEvent(event.getEntity());
                    //?} else {
                    /*ModEvents.playerJoinEvent(event.getPlayer());
                    *///?}
                }
            });
            //? if >=1.19.2 {
            MinecraftForge.EVENT_BUS.<ScreenEvent.Init.Pre>addListener(event -> {
            //?} else if 1.18.2 {
            /*MinecraftForge.EVENT_BUS.<ScreenEvent.InitScreenEvent.Pre>addListener(event -> {
            *///?} else {
            /*MinecraftForge.EVENT_BUS.<GuiScreenEvent.InitGuiEvent.Pre>addListener(event -> {
            *///?}
                if (LanguageUtils.isChineseLanguage()) {
                    //? if >=1.18.2 {
                    ModEvents.screenAfterInitEvent(event.getScreen());
                    //?} else {
                    /*ModEvents.screenAfterInitEvent(event.getGui());
                    *///?}
                }
            });
            //? if >=1.18.2 {
            MinecraftForge.EVENT_BUS.<RegisterClientCommandsEvent>addListener(event -> event.getDispatcher().register(
            //?} else {
            /*MinecraftForge.EVENT_BUS.<RegisterCommandsEvent>addListener(event -> event.getDispatcher().register(
            *///?}
                    Commands.literal("vmtu")
                            .then(Commands.literal("check")
                                    .executes(context -> {
                                        //? if >=1.19.2 {
                                        ModEvents.playerJoinEvent(context.getSource().getPlayer());
                                        //?} else {
                                        /*ModEvents.playerJoinEvent(context.getSource().getPlayerOrException());
                                        *///?}
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
            ));

            modEventBus.<FMLConstructModEvent>addListener(event -> VMTranslationUpdate.autoDownloadAndLoadPack());
        }
    }
}
