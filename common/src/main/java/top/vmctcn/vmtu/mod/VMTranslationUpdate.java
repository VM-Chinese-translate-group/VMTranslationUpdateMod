package top.vmctcn.vmtu.mod;

import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.pack.ResourcePackIndex;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoWriter;
import top.vmctcn.vmtu.mod.modpack.metadata.ModpackMetadata;
import top.vmctcn.vmtu.mod.modpack.metadata.ModpackMetadataReader;
import top.vmctcn.vmtu.mod.modpack.updater.VMMetadata;
import top.vmctcn.vmtu.mod.modpack.updater.VMMetadataReader;

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

        if (ModConfigHelper.getConfig().misc.devMode) {
            ModpackInfo.Translation translation = modpackInfo.getTranslation();
            VMMetadata.Modpacks vmmetadata = VMMetadataReader.getModpack(translation.getId());

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
            ModContexts.LOGGER.warn("Modpack Online Version: {}", vmmetadata.getModpackVersion());
            ModContexts.LOGGER.warn("Modpack Online Translation Version: {}", vmmetadata.getTranslationVersion());
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
        boolean autoDownloadPack = ModConfigHelper.getConfig().resourcePack.autoDownloadVMTranslationPack;
        boolean autoLoadExtraPack = ModConfigHelper.getConfig().resourcePack.autoLoadExtraTranslationPack;
        String gameVersion = ModPlatform.INSTANCE.getGameVersion();
        String extraPackName = ModConfigHelper.getConfig().resourcePack.extraPackName;
        ResourcePackIndex resourcePackIndex = ModConfigHelper.getConfig().resourcePack.resourcePackIndex;
        int extraPackCustomIndex = ModConfigHelper.getConfig().resourcePack.extraPackCustomIndex;
        String resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
        VMTUCore.init(ModPlatform.INSTANCE.getGameDir(), gameVersion, resPackName, extraPackName, resourcePackIndex, extraPackCustomIndex, autoDownloadPack, autoLoadExtraPack);
    }
}