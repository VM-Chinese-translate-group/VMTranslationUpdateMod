package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.vmctcn.vmtranslationupdate.config.ModConfig;
import top.vmctcn.vmtranslationupdate.event.ModEventHandler;
import top.vmctcn.vmtranslationupdate.util.PackDownloadUtil;

import java.util.Random;

@Mod(modid = VMTranslationUpdate.MOD_ID, name = VMTranslationUpdate.MODNAME, version = VMTranslationUpdate.MOD_VERSION)
public class VMTranslationUpdate {
    public static Random random;
    public static int tickCounter;
    public static final String MODNAME = "VMTranslationUpdate";
    public static final String MOD_ID = "vmtranslationupdate";
    public static final String MOD_VERSION = "2.3.2";
    public static final Minecraft client = Minecraft.getMinecraft();
    public static final Logger LOGGER = LogManager.getLogger(MODNAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (ModConfig.autoSwitchLanguage && ModConfig.switchLanguage != null) {
            client.gameSettings.language = ModConfig.switchLanguage;
        }

        if (ModConfig.autoDownloadVMTranslationPack) {
            PackDownloadUtil.downloadResPack();
        }

        ModConfig.config = new Configuration(event.getSuggestedConfigurationFile());
        ModConfig.syncConfig();

        MinecraftForge.EVENT_BUS.register(ModEventHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
        random = new Random();
    }
}