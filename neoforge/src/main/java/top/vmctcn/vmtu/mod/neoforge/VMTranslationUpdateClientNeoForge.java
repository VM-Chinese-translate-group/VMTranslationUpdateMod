package top.vmctcn.vmtu.mod.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent.Init.Pre;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import top.vmctcn.vmtu.mod.command.ModCommands;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.options.GameOptionsSetter;

@Mod(value = VMTranslationUpdate.MOD_ID, dist = Dist.CLIENT)
public class VMTranslationUpdateClientNeoForge {
    public VMTranslationUpdateClientNeoForge(ModContainer modContainer) {
        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            GameOptionsSetter.init(FMLPaths.GAMEDIR.get());

            NeoHelper.registerConfigScreen(modContainer, screen -> AutoConfig.getConfigScreen(ModConfigs.class, screen).get());

            NeoForge.EVENT_BUS.addListener(PlayerLoggedInEvent.class, event -> ModEvents.playerJoinEvent());

            NeoForge.EVENT_BUS.addListener(Pre.class, event -> ModEvents.screenAfterInitEvent(event.getScreen()));
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterClientCommandsEvent event) {
        ModCommands.VMTUCommand();
    }
}