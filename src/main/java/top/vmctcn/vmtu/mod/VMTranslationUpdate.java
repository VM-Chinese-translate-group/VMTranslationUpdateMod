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
        ModpackInfo.Modpack modpackInfo = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackMetadata modpackMetadata = ModpackMetadataReader.getMetadata();
        if (modpackMetadata != null) {
            if (!Objects.equals(modpackInfo.getVersion(), modpackMetadata.getModpackVersion())) {
                ModpackInfoWriter.syncModpackVersion(modpackMetadata.getModpackVersion());
            }
        }

        LanguageUtils.autoSwitchLanguage();

        if (ModConfigs.misc.devMode) {
            ModpackInfo.Translation translation = modpackInfo.getTranslation();

            VMMetadata.Modpacks meta = VMMetadataReader.getModpack(translation.getId());

            ModContexts.LOGGER.warn("==================== VMTU Dev Mode ====================");
            ModContexts.LOGGER.warn("Modpack Name: {}", modpackInfo.getName());
            ModContexts.LOGGER.warn("Modpack Version: {}", modpackInfo.getVersion());
            ModContexts.LOGGER.warn("Modpack Translation URL: {}", translation.getUrl());
            if (translation.getUpdateCheckUrl() != null) {
                ModContexts.LOGGER.warn("Modpack Translation Update Check URL: {}", translation.getUpdateCheckUrl());
            }
            ModContexts.LOGGER.warn("Modpack Translation Language: {}", translation.getLanguage());
            ModContexts.LOGGER.warn("Modpack Translation Version: {}", translation.getVersion());
            ModContexts.LOGGER.warn("Modpack Translation Resource Pack Name: {}", translation.getResourcePackName());
            ModContexts.LOGGER.warn("Meta Url: {}", VMMetadataReader.getMetaUrl());
            ModContexts.LOGGER.warn("Meta Version: {}", VMMetadataReader.getMetadata().getMetaVersion());
            ModContexts.LOGGER.warn("Modpack Online Version: {}", meta.getModpackVersion());
            ModContexts.LOGGER.warn("Modpack Online Translation Version: {}", meta.getTranslationVersion());
            if (modpackMetadata != null) {
                ModContexts.LOGGER.warn("Modpack Metadata Type: {}", modpackMetadata.getMetadataType());
                ModContexts.LOGGER.warn("Modpack Metadata File Name: {}", modpackMetadata.getMetadataType().getMetadataFileName());
                ModContexts.LOGGER.warn("Modpack Metadata Version: {}", modpackMetadata.getModpackVersion());
                ModContexts.LOGGER.warn("Modpack Name in Metadata: {}", modpackMetadata.getModpackName());
            } else {
                ModContexts.LOGGER.warn("Modpack Metadata: null");
            }
            ModContexts.LOGGER.warn("=======================================================");
        }
    }

    public static void autoDownloadAndLoadPack() {
        boolean autoDownloadPack = ModConfigs.resourcePack.autoDownloadVMTranslationPack;
        boolean autoLoadExtraPack = ModConfigs.resourcePack.autoLoadExtraTranslationPack;
        String gameVersion = ModPlatform.getGameVersion();
        String extraPackName = ModConfigs.resourcePack.extraPackName;
        ResourcePackIndex resourcePackIndex = ModConfigs.resourcePack.resourcePackIndex;
        int extraPackCustomIndex = ModConfigs.resourcePack.extraPackCustomIndex;
        String resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
        VMTUCore.init(ModPlatform.getGameDir(), gameVersion, resPackName, extraPackName, resourcePackIndex, extraPackCustomIndex, autoDownloadPack, autoLoadExtraPack);
    }
}