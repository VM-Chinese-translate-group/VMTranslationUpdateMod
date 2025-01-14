package top.vmctcn.vmtranslationupdate.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.vmctcn.vmtranslationupdate.ModEvents;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfigs;
import top.vmctcn.vmtranslationupdate.respack.GameOptionsSetter;

@Mod(value = VMTranslationUpdate.MOD_ID, dist = Dist.CLIENT)
public class VMTranslationUpdateClientNeoForge {
    public VMTranslationUpdateClientNeoForge() {
        IEventBus forgeEventBus = NeoForge.EVENT_BUS;
        ModLoadingContext context = ModLoadingContext.get();

        context.registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> AutoConfig.getConfigScreen(ModConfigs.class, screen).get());

        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            GameOptionsSetter.init(FMLPaths.GAMEDIR.get());

            forgeEventBus.addListener(PlayerEvent.PlayerLoggedInEvent.class, event -> {
                ModEvents.playerJoinEvent((ServerPlayerEntity) event.getEntity());
            });

            forgeEventBus.addListener(ScreenEvent.Init.Pre.class, event -> {
                ModEvents.screenAfterInitEvent(event.getScreen());
            });
        }
    }
}
