package top.vmctcn.vmtu.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.pack.ResourcePackIndex;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoWriter;
import top.vmctcn.vmtu.mod.modpack.metadata.ModpackMetadata;
import top.vmctcn.vmtu.mod.modpack.metadata.ModpackMetadataReader;
import top.vmctcn.vmtu.mod.modpack.updater.VMMetadata;
import top.vmctcn.vmtu.mod.modpack.updater.VMMetadataReader;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;

import java.util.Objects;

public class VMTranslationUpdate {
    public static final String MOD_ID = "vmtranslationupdate";
    public static final String MOD_NAME = "VMTranslationUpdateMod";
    public static Logger LOGGER = LogManager.getLogger("VMTranslationUpdateMod");

    public static void init() {
        ModpackInfo.Modpack modpackInfo = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackMetadata modpackMetadata = ModpackMetadataReader.getMetadata();
        if (modpackMetadata != null && !Objects.equals(modpackInfo.getVersion(), modpackMetadata.getModpackVersion())) {
            ModpackInfoWriter.syncModpackVersion(modpackMetadata.getModpackVersion());
        }

        LanguageUtils.autoSwitchLanguage();

        if (ModConfigs.devMode) {
            ModpackInfo.Translation translation = modpackInfo.getTranslation();

            VMMetadata.Modpacks meta = VMMetadataReader.getModpack(translation.getId());

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
            LOGGER.warn("Modpack Online Version: {}", meta.getModpackVersion());
            LOGGER.warn("Modpack Online Translation Version: {}", meta.getTranslationVersion());
            LOGGER.warn("=======================================================");
        }
    }

    public static void autoDownloadAndLoadPack() {
        boolean autoDownloadPack = ModConfigs.autoDownloadVMTranslationPack;
        boolean autoLoadExtraPack = ModConfigs.autoLoadExtraTranslationPack;
        String gameVersion = ModPlatform.getGameVersion();
        String extraPackName = ModConfigs.extraPackName;
        ResourcePackIndex resourcePackIndex = ModConfigs.resourcePackIndex;
        int extraPackCustomIndex = ModConfigs.extraPackCustomIndex;
        String resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
        VMTUCore.init(ModPlatform.getGameDir(), gameVersion, resPackName, extraPackName, resourcePackIndex, extraPackCustomIndex, autoDownloadPack, autoLoadExtraPack);
    }
}