package top.vmctcn.vmtu.mod.forge;

import com.mojang.brigadier.Command;
import net.minecraft.server.command.CommandManager;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.mod.options.GameOptionsSetter;

@Mod(VMTranslationUpdate.MOD_ID)
public class VMTranslationUpdateClientForge {
    public VMTranslationUpdateClientForge() {
        ForgeHelper.getClientModIgnoredServerOnly(VMTranslationUpdate.MOD_ID);

        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            GameOptionsSetter.init(FMLPaths.GAMEDIR.get());

            ForgeHelper.registerConfigScreen(VMTranslationUpdate.MOD_ID, ModConfigHelper::setConfigScreen);

            MinecraftForge.EVENT_BUS.<PlayerEvent.PlayerLoggedInEvent>addListener(event -> {
                if (LanguageHelper.isChineseLanguage()) {
                    ModEvents.playerJoinEvent(event.getPlayer());
                }
            });
            MinecraftForge.EVENT_BUS.<GuiScreenEvent.InitGuiEvent.Pre>addListener(event -> {
                if (LanguageHelper.isChineseLanguage()) {
                    ModEvents.screenAfterInitEvent(event.getGui());
                }
            });

            MinecraftForge.EVENT_BUS.<RegisterCommandsEvent>addListener(event -> event.getDispatcher().register(
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