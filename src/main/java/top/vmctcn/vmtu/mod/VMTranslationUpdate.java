package top.vmctcn.vmtu.mod;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.vmctcn.vmtu.mod.command.ModCommand;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.meta.Metadata;
import top.vmctcn.vmtu.mod.modpack.meta.MetadataReader;
import top.vmctcn.vmtu.mod.options.GameOptionsSetter;

@Mod(modid = VMTranslationUpdate.MOD_ID, name = VMTranslationUpdate.MOD_NAME, clientSideOnly = true)
public class VMTranslationUpdate {
    public static final String MOD_ID = "vmtranslationupdate";
    public static final String MOD_NAME = "VMTranslationUpdate";
    public static Logger LOGGER = LogManager.getLogger(VMTranslationUpdate.MOD_NAME);

    @Mod.EventHandler
    public void onConstruct(FMLConstructionEvent event) {
        MetadataReader.init();
        ModpackInfoReader.init();

        if (ModConfigs.testMode) {
            ModpackInfo.Modpack modpackInfo = ModpackInfoReader.getModpackInfo().getModpack();
            ModpackInfo.Translation translation = modpackInfo.getTranslation();

            Metadata.Modpacks meta = MetadataReader.getModpack(translation.getId());

            LOGGER.warn("==================== VMTU testMode ====================");
            LOGGER.warn("Modpack Name: {}", modpackInfo.getName());
            LOGGER.warn("Modpack Version: {}", modpackInfo.getVersion());
            LOGGER.warn("Modpack Translation URL: {}", translation.getUrl());
            LOGGER.warn("Modpack Translation Update Check URL: {}", translation.getUpdateCheckUrl());
            LOGGER.warn("Modpack Translation Language: {}", translation.getLanguage());
            LOGGER.warn("Modpack Translation Version: {}", translation.getVersion());
            LOGGER.warn("Modpack Translation Resource Pack Name: {}", translation.getResourcePackName());
            LOGGER.warn("Meta Url: {}", MetadataReader.getMetaUrl());
            LOGGER.warn("Meta Version: {}", MetadataReader.getMetadata().getMetaVersion());
            LOGGER.warn("Modpack Online Version: {}", meta.getModpackVersion());
            LOGGER.warn("Modpack Online Translation Version: {}", meta.getTranslationVersion());
            LOGGER.warn("=======================================================");
        }

        GameOptionsSetter.init(ModPlatform.getGameDir());

        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {



    }

    @EventHandler
    public static void onInit(FMLInitializationEvent event) {
        ClientCommandHandler.instance.register(new ModCommand());
    }
}