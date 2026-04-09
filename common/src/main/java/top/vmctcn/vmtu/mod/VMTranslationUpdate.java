package top.vmctcn.vmtu.mod;

import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.pack.ResourcePackIndex;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.config.ModConfigs;
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

    @SuppressWarnings({"ConstantConditions"})
    public static void init() {
        ModConfigs config = ModConfigHelper.getConfig();
        ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackMetadata modpackMetadata = ModpackMetadataReader.getMetadata();

        syncModpackVersion(modpack, modpackMetadata);
        LanguageUtils.autoSwitchLanguage();

        if (config.misc.devMode || ModPlatform.getInstance().isDevelopmentEnvironment()) {
            logDevInfo(modpack, modpackMetadata);
        }
    }

    public static void autoDownloadAndLoadPack() {
        ModConfigs.ResourcePack resourcePackConfig = ModConfigHelper.getConfig().resourcePack;
        String gameVersion = ModPlatform.getInstance().getGameVersion();
        String extraPackName = resourcePackConfig.extraPackName;
        ResourcePackIndex resourcePackIndex = resourcePackConfig.resourcePackIndex;
        int extraPackCustomIndex = resourcePackConfig.extraPackCustomIndex;
        boolean autoDownloadPack = resourcePackConfig.autoDownloadVMTranslationPack;
        boolean autoLoadExtraPack = resourcePackConfig.autoLoadExtraTranslationPack;

        VMTUCore.init(
                ModPlatform.getInstance().getGameDir(),
                gameVersion,
                extraPackName,
                resourcePackIndex,
                extraPackCustomIndex,
                autoDownloadPack,
                autoLoadExtraPack
        );
    }

    private static void syncModpackVersion(ModpackInfo.Modpack modpack, ModpackMetadata modpackMetadata) {
        if (modpackMetadata == null || ModpackInfoReader.isExampleModpackInfo()) {
            return;
        }

        if (!Objects.equals(modpack.getVersion(), modpackMetadata.getModpackVersion())) {
            ModpackInfoWriter.syncModpackVersion(modpackMetadata.getModpackVersion());
        }
    }

    @SuppressWarnings("deprecation")
    private static void logDevInfo(ModpackInfo.Modpack modpack, ModpackMetadata modpackMetadata) {
        ModpackInfo.Translation translation = modpack.getTranslation();
        VMMetadata metadata = VMMetadataReader.getMetadata();
        VMMetadata.Modpacks vmMetadata = metadata != null ? VMMetadataReader.getModpack(translation.getId()) : null;

        ModContexts.LOGGER.info("=================== VMTU Dev Mode ====================");
        ModContexts.LOGGER.info("Modpack Name: {}", modpack.getName());
        ModContexts.LOGGER.info("Modpack Version: {}", modpack.getVersion());
        ModContexts.LOGGER.info("Modpack Translation URL: {}", translation.getUrl());
        logIfPresent("Modpack Translation Update Check URL: {}", translation.getUpdateCheckUrl());
        ModContexts.LOGGER.info("Modpack Translation Language: {}", translation.getLanguage());
        ModContexts.LOGGER.info("Modpack Translation Version: {}", translation.getVersion());
        logIfPresent("Translation Resource Pack Name: {}", translation.getResourcePackName());
        ModContexts.LOGGER.info("Meta Url: {}", VMMetadataReader.getMetaUrl());
        ModContexts.LOGGER.info("Meta Version: {}", metadata != null ? metadata.getMetaVersion() : null);
        ModContexts.LOGGER.info("Modpack Online Version: {}", vmMetadata != null ? vmMetadata.getModpackVersion() : null);
        ModContexts.LOGGER.info("Modpack Online Translation Version: {}", vmMetadata != null ? vmMetadata.getTranslationVersion() : null);

        if (modpackMetadata == null) {
            ModContexts.LOGGER.info("Modpack Metadata: null");
        } else {
            ModContexts.LOGGER.info("Modpack Metadata Type: {}", modpackMetadata.getMetadataType());
            ModContexts.LOGGER.info("Modpack Metadata File Name: {}", modpackMetadata.getMetadataType().getMetadataFileName());
            ModContexts.LOGGER.info("Modpack Metadata Version: {}", modpackMetadata.getModpackVersion());
            ModContexts.LOGGER.info("Modpack Name in Metadata: {}", modpackMetadata.getModpackName());
        }

        ModContexts.LOGGER.info("=====================================================");
    }

    private static void logIfPresent(String message, Object value) {
        if (value != null) {
            ModContexts.LOGGER.info(message, value);
        }
    }
}
