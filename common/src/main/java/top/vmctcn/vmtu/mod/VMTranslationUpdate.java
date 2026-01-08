package top.vmctcn.vmtu.mod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoWriter;
import top.vmctcn.vmtu.mod.modpack.metadata.ModpackMetadata;
import top.vmctcn.vmtu.mod.modpack.metadata.ModpackMetadataReader;
import top.vmctcn.vmtu.mod.modpack.updater.VMMetadata;
import top.vmctcn.vmtu.mod.modpack.updater.VMMetadataReader;
import top.vmctcn.vmtu.mod.options.GameOptionsSetter;

import java.util.Objects;

public class VMTranslationUpdate {
    public static final String MOD_ID = "vmtranslationupdate";
    public static final Logger LOGGER = LoggerFactory.getLogger("VMTranslationUpdateMod");

    public static void init() {
        ModpackInfo.Modpack modpackInfo = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackMetadata modpackMetadata = ModpackMetadataReader.getMetadata();
        if (modpackMetadata != null && !Objects.equals(modpackInfo.getVersion(), modpackMetadata.getModpackVersion())) {
            ModpackInfoWriter.syncModpackVersion(modpackMetadata.getModpackVersion());
        }

        GameOptionsSetter.autoSwitchLanguage();

        if (ModConfigHelper.getConfig().devMode) {
            ModpackInfo.Translation translation = modpackInfo.getTranslation();
            VMMetadata.Modpacks vmmetadata = VMMetadataReader.getModpack(translation.getId());

            LOGGER.warn("==================== VMTU Dev Mode ====================");
            LOGGER.warn("Modpack Name: {}", modpackInfo.getName());
            LOGGER.warn("Modpack Version: {}", modpackInfo.getVersion());
            LOGGER.warn("Modpack Translation URL: {}", translation.getUrl());
            if (translation.getUpdateCheckUrl() != null) {
                LOGGER.warn("Modpack Translation Update Check URL: {}", translation.getUpdateCheckUrl());
            }
            LOGGER.warn("Modpack Translation Language: {}", translation.getLanguage());
            LOGGER.warn("Modpack Translation Version: {}", translation.getVersion());
            LOGGER.warn("Modpack Translation Resource Pack Name: {}", translation.getResourcePackName());
            LOGGER.warn("Meta Url: {}", VMMetadataReader.getMetaUrl());
            LOGGER.warn("Meta Version: {}", VMMetadataReader.getMetadata().getMetaVersion());
            LOGGER.warn("Modpack Online Version: {}", vmmetadata.getModpackVersion());
            LOGGER.warn("Modpack Online Translation Version: {}", vmmetadata.getTranslationVersion());
            if (modpackMetadata != null) {
                LOGGER.warn("Modpack Metadata Type: {}", modpackMetadata.getMetadataType());
                LOGGER.warn("Modpack Metadata File Name: {}", modpackMetadata.getMetadataType().getMetadataFileName());
                LOGGER.warn("Modpack Metadata Version: {}", modpackMetadata.getModpackVersion());
                LOGGER.warn("Modpack Name in Metadata: {}", modpackMetadata.getModpackName());
            } else {
                LOGGER.warn("Modpack Metadata: null");
            }
            LOGGER.warn("=======================================================");
        }
    }
}