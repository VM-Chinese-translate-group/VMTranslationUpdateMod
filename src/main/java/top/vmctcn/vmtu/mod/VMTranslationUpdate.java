package top.vmctcn.vmtu.mod;

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

    public static void init() {
        ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackMetadata modpackMetadata = ModpackMetadataReader.getMetadata();
        if (modpackMetadata != null && !ModpackInfoReader.isExampleModpackInfo()) {
            if (!Objects.equals(modpack.getVersion(), modpackMetadata.getModpackVersion())) {
                ModpackInfoWriter.syncModpackVersion(modpackMetadata.getModpackVersion());
            }
        }

        LanguageUtils.autoSwitchLanguage();

        if (ModConfigs.misc.devMode) {
            ModpackInfo.Translation translation = modpack.getTranslation();
            VMMetadata.Modpacks metadata = VMMetadataReader.getModpack(translation.getId());

            ModContexts.LOGGER.info("=================== VMTU Dev Mode ====================");
            ModContexts.LOGGER.info("Modpack Name: {}", modpack.getName());
            ModContexts.LOGGER.info("Modpack Version: {}", modpack.getVersion());
            ModContexts.LOGGER.info("Modpack Translation URL: {}", translation.getUrl());
            if (translation.getUpdateCheckUrl() != null) {
                ModContexts.LOGGER.info("Modpack Translation Update Check URL: {}", translation.getUpdateCheckUrl());
            }
            ModContexts.LOGGER.info("Modpack Translation Language: {}", translation.getLanguage());
            ModContexts.LOGGER.info("Modpack Translation Version: {}", translation.getVersion());
            if (translation.getResourcePackName() != null) {
                ModContexts.LOGGER.info("Translation Resource Pack Name: {}", translation.getResourcePackName());
            }
            ModContexts.LOGGER.info("Meta Url: {}", VMMetadataReader.getMetaUrl());
            ModContexts.LOGGER.info("Meta Version: {}", VMMetadataReader.getMetadata().getMetaVersion());
            ModContexts.LOGGER.info("Modpack Online Version: {}", metadata.getModpackVersion());
            ModContexts.LOGGER.info("Modpack Online Translation Version: {}", metadata.getTranslationVersion());
            if (modpackMetadata != null) {
                ModContexts.LOGGER.info("Modpack Metadata Type: {}", modpackMetadata.getMetadataType());
                ModContexts.LOGGER.info("Modpack Metadata File Name: {}", modpackMetadata.getMetadataType().getMetadataFileName());
                ModContexts.LOGGER.info("Modpack Metadata Version: {}", modpackMetadata.getModpackVersion());
                ModContexts.LOGGER.info("Modpack Name in Metadata: {}", modpackMetadata.getModpackName());
            } else {
                ModContexts.LOGGER.info("Modpack Metadata: null");
            }
            ModContexts.LOGGER.info("=====================================================");
        }
    }

    public static void autoDownloadAndLoadPack() {
        boolean autoDownloadPack = ModConfigs.resourcePack.autoDownloadVMTranslationPack;
        boolean autoLoadExtraPack = ModConfigs.resourcePack.autoLoadExtraTranslationPack;
        String gameVersion = ModPlatform.getGameVersion();
        String extraPackName = ModConfigs.resourcePack.extraPackName;
        ResourcePackIndex resourcePackIndex = ModConfigs.resourcePack.resourcePackIndex;
        int extraPackCustomIndex = ModConfigs.resourcePack.extraPackCustomIndex;
        VMTUCore.init(ModPlatform.getGameDir(), gameVersion, extraPackName, resourcePackIndex, extraPackCustomIndex, autoDownloadPack, autoLoadExtraPack);
    }
}