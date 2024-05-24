package top.vmctcn.vmtranslationupdate.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.vmctcn.vmtranslationupdate.ModEvents;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfigs;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;

@Mod(VMTranslationUpdate.MOD_ID)
public class VMTranslationUpdateClientNeoForge {
    public VMTranslationUpdateClientNeoForge() {
        IEventBus forgeEventBus = NeoForge.EVENT_BUS;
        ModLoadingContext context = ModLoadingContext.get();

        context.registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
        context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> AutoConfig.getConfigScreen(ModConfigs.class, screen).get()));

        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            forgeEventBus.addListener(EventPriority.HIGHEST, TickEvent.ClientTickEvent.class, event -> {
                MinecraftClient client = MinecraftClient.getInstance();

                if (event.phase == TickEvent.Phase.END && ModConfigUtil.getConfig().displayTips) {
                    if (client.player == null) return;
                    ModEvents.clientTickEndEvent(client);
                }
            });

            forgeEventBus.addListener(EventPriority.HIGHEST, PlayerEvent.PlayerLoggedInEvent.class, event -> {
                ModEvents.playerJoinEvent((ServerPlayerEntity) event.getEntity());
            });
        }
    }
}
