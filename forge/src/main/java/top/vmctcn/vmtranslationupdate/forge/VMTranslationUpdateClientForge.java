package top.vmctcn.vmtranslationupdate.forge;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import top.vmctcn.vmtranslationupdate.ModEvents;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfigs;
import top.vmctcn.vmtranslationupdate.respack.GameOptionsSetter;

@Mod(VMTranslationUpdate.MOD_ID)
public class VMTranslationUpdateClientForge {
    public VMTranslationUpdateClientForge() {
        ForgeHelper.getClientModIgnoredServerOnly(VMTranslationUpdate.MOD_ID);

        if (FMLLoader.getDist().isClient()) {
            VMTranslationUpdate.init();

            GameOptionsSetter.init();

            ForgeHelper.registerConfigScreen(VMTranslationUpdate.MOD_ID, screen -> AutoConfig.getConfigScreen(ModConfigs.class, screen).get());

            MinecraftForge.EVENT_BUS.<PlayerEvent.PlayerLoggedInEvent>addListener(event -> {
                ModEvents.playerJoinEvent((ServerPlayerEntity) event.getEntity());
            });

            MinecraftForge.EVENT_BUS.<ScreenEvent.Init.Pre>addListener(event -> {
                ModEvents.screenAfterInitEvent(event.getScreen());
            });
        }
    }
}
