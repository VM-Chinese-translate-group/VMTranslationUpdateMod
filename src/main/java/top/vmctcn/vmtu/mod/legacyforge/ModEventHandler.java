package top.vmctcn.vmtu.mod.legacyforge;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModEvents;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;

@Mod.EventBusSubscriber(modid = ModContexts.MOD_ID)
public final class ModEventHandler {

    @SubscribeEvent
    public void playerJoinEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (LanguageUtils.isChineseLanguage()) {
            ModEvents.playerJoinEvent(event.player);
        }
    }

    @SubscribeEvent
    public void screenAfterInitEvent(GuiScreenEvent.InitGuiEvent.Pre event) {
        if (LanguageUtils.isChineseLanguage()) {
            ModEvents.screenAfterInitEvent(event.getGui());
        }
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ModContexts.MOD_ID)) {
            ConfigManager.sync(ModContexts.MOD_ID, Config.Type.INSTANCE);
            ModContexts.LOGGER.info("Config Change saved");
        }
    }
}
