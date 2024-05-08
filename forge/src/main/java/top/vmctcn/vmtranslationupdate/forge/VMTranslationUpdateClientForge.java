package top.vmctcn.vmtranslationupdate.forge;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import top.vmctcn.vmtranslationupdate.ModEvents;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfigs;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;

@Mod(VMTranslationUpdate.MOD_ID)
public class VMTranslationUpdateClientForge {
    public VMTranslationUpdateClientForge() {
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        ModLoadingContext context = ModLoadingContext.get();

        context.registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
        context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> AutoConfig.getConfigScreen(ModConfigs.class, screen).get()));

        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            forgeEventBus.<TickEvent.ClientTickEvent>addListener(event -> {
                MinecraftClient client = MinecraftClient.getInstance();

                if (event.phase == TickEvent.Phase.END && ModConfigUtil.getConfig().displayTips) {
                    if (client.player == null) return;
                    ModEvents.clientTickEndEvent(client);
                }
            });

            forgeEventBus.<PlayerEvent.PlayerLoggedInEvent>addListener(event -> {
                ModEvents.playerJoinEvent((ServerPlayerEntity) event.getEntity());
            });
        }
    }
}
