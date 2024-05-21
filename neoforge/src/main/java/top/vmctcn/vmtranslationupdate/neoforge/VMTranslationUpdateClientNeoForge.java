package top.vmctcn.vmtranslationupdate.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
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

        context.registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> AutoConfig.getConfigScreen(ModConfigs.class, screen).get());

        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            forgeEventBus.addListener(ClientTickEvent.Post.class, event -> {
                MinecraftClient client = MinecraftClient.getInstance();
                if (ModConfigUtil.getConfig().displayTips) {
                    ModEvents.clientTickEndEvent(client);
                }
            });

            forgeEventBus.addListener(PlayerEvent.PlayerLoggedInEvent.class, event -> {
                ModEvents.playerJoinEvent((ServerPlayerEntity) event.getEntity());
            });
        }
    }
}
