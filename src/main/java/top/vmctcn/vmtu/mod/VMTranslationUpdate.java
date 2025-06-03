package top.vmctcn.vmtu.mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.modpack.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.ModpackInfoReader;
import top.vmctcn.vmtu.mod.options.GameOptionsSetter;

@Mod(modid = VMTranslationUpdate.MOD_ID, name = VMTranslationUpdate.MOD_NAME, guiFactory = "top.vmctcn.vmtu.mod.config.ModConfigScreenFactory", clientSideOnly = true)
public class VMTranslationUpdate {
    public static final String MOD_ID = "vmtranslationupdate";
    public static final String MOD_NAME = "VMTranslationUpdate";
    public static final Logger LOGGER = LogManager.getLogger(VMTranslationUpdate.MOD_NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (ModConfigs.testMode) {
            ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
            ModpackInfo.Translation translation = modpack.getTranslation();

            LOGGER.warn("Modpack Name: {}", modpack.getName());
            LOGGER.warn("Modpack Version: {}", modpack.getVersion());
            LOGGER.warn("Modpack Translation URL: {}", translation.getUrl());
            LOGGER.warn("Modpack Translation Update Check URL: {}", translation.getUpdateCheckUrl());
            LOGGER.warn("Modpack Translation Language: {}", translation.getLanguage());
            LOGGER.warn("Modpack Translation Version: {}", translation.getVersion());
            LOGGER.warn("Modpack Translation Resource Pack Name: {}", translation.getResourcePackName());
        }

        GameOptionsSetter.init(ModPlatform.getConfigDir());
        ModpackInfoReader.init();

        ModConfigs.config = new Configuration(event.getSuggestedConfigurationFile());
        ModConfigs.syncConfig();

        MinecraftForge.EVENT_BUS.register(new ModEvents());
        MinecraftForge.EVENT_BUS.register(this);
    }
}