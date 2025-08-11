package top.vmctcn.vmtu.mod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.meta.Metadata;
import top.vmctcn.vmtu.mod.modpack.meta.MetadataReader;

public class VMTranslationUpdate {
    public static final String MODNAME = "VMTranslationUpdate";
    public static final String MOD_ID = "vmtranslationupdate";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);

    public static void init() {
        if (ModConfigHelper.getConfig().testMode) {
            ModpackInfo.Modpack modpackInfo = ModpackInfoReader.getModpackInfo().getModpack();
            ModpackInfo.Translation translation = modpackInfo.getTranslation();

            Metadata.Modpacks meta = MetadataReader.getModpack(modpackInfo.getName());

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
    }
}
