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
    @SuppressWarnings({"ConstantConditions", "deprecation"})
    public static void init() {
        ModpackInfo.Modpack modpackInfo = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackMetadata modpackMetadata = ModpackMetadataReader.getMetadata();
        if (modpackMetadata != null) {
            if (!Objects.equals(modpackInfo.getVersion(), modpackMetadata.getModpackVersion())) {
                ModpackInfoWriter.syncModpackVersion(modpackMetadata.getModpackVersion());
            }
        }

        LanguageUtils.autoSwitchLanguage();

        if (ModConfigHelper.getConfig().misc.devMode || ModPlatform.isDevelopmentEnvironment()) {
            ModpackInfo.Translation translation = modpackInfo.getTranslation();
            VMMetadata.Modpacks vmmetadata = VMMetadataReader.getModpack(translation.getId());

            ModContexts.LOGGER.info("============ VMTU Dev Mode ==============");
            ModContexts.LOGGER.info("Modpack Name: {}", modpackInfo.getName());
            ModContexts.LOGGER.info("Modpack Version: {}", modpackInfo.getVersion());
            ModContexts.LOGGER.info("Modpack Translation URL: {}", translation.getUrl());
            if (translation.getUpdateCheckUrl() != null) {
                ModContexts.LOGGER.info("Modpack Translation Update Check URL: {}", translation.getUpdateCheckUrl());
            }
            ModContexts.LOGGER.info("Modpack Translation Language: {}", translation.getLanguage());
            ModContexts.LOGGER.info("Modpack Translation Version: {}", translation.getVersion());
            ModContexts.LOGGER.info("Translation Resource Pack Name: {}", translation.getResourcePackName());
            ModContexts.LOGGER.info("Meta Url: {}", VMMetadataReader.getMetaUrl());
            ModContexts.LOGGER.info("Meta Version: {}", VMMetadataReader.getMetadata().getMetaVersion());
            ModContexts.LOGGER.info("Modpack Online Version: {}", vmmetadata.getModpackVersion());
            ModContexts.LOGGER.info("Modpack Online Translation Version: {}", vmmetadata.getTranslationVersion());
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
        boolean autoDownloadPack = ModConfigHelper.getConfig().resourcePack.autoDownloadVMTranslationPack;
        boolean autoLoadExtraPack = ModConfigHelper.getConfig().resourcePack.autoLoadExtraTranslationPack;
        String gameVersion = ModPlatform.getGameVersion();
        String extraPackName = ModConfigHelper.getConfig().resourcePack.extraPackName;
        ResourcePackIndex resourcePackIndex = ModConfigHelper.getConfig().resourcePack.resourcePackIndex;
        int extraPackCustomIndex = ModConfigHelper.getConfig().resourcePack.extraPackCustomIndex;
        String resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
        VMTUCore.init(ModPlatform.getGameDir(), gameVersion, resPackName, extraPackName, resourcePackIndex, extraPackCustomIndex, autoDownloadPack, autoLoadExtraPack);
    }
}