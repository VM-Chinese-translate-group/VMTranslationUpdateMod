package top.vmctcn.vmtranslationupdate.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.vmctcn.vmtranslationupdate.ModEvents;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfigs;
import top.vmctcn.vmtranslationupdate.respack.GameOptionsSetter;

@Mod(VMTranslationUpdate.MOD_ID)
public class VMTranslationUpdateClientNeoForge {
    public VMTranslationUpdateClientNeoForge() {
        NeoHelper.getClientModIgnoredServerOnly(VMTranslationUpdate.MOD_ID);

        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            GameOptionsSetter.init(FMLPaths.GAMEDIR.get());

            NeoHelper.registerConfigScreen(VMTranslationUpdate.MOD_ID, screen -> AutoConfig.getConfigScreen(ModConfigs.class, screen).get());

            NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedInEvent.class, event -> {
                ModEvents.playerJoinEvent((ServerPlayerEntity) event.getEntity());
            });

            NeoForge.EVENT_BUS.addListener(ScreenEvent.Init.Pre.class, event -> {
                ModEvents.screenAfterInitEvent(event.getScreen());
            });
        }
    }
}
