package top.vmctcn.vmtranslationupdate.forge;

import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdateMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.vmctcn.vmtranslationupdate.util.ConfigUtil;

@Mod(VMTranslationUpdateMod.MOD_ID)
public class VMTranslationUpdateModForge {
    public VMTranslationUpdateModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(VMTranslationUpdateMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        IEventBus modEventBus = EventBuses.getModEventBus(VMTranslationUpdateMod.MOD_ID).get();

        modEventBus.addListener(this::onInitialize);
        modEventBus.addListener(this::onInitializeClient);
    }

    public void onInitialize(FMLCommonSetupEvent event) {
        VMTranslationUpdateMod.init();
    }

    public void onInitializeClient(FMLClientSetupEvent event) {
        ConfigUtil.setConfigScreen();
    }
}
