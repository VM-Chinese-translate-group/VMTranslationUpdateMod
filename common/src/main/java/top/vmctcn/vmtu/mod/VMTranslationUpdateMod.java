package top.vmctcn.vmtu.mod;

import top.vmctcn.vmtu.libraries.common.CommonContexts;
import top.vmctcn.vmtu.libraries.modpack.info.api.ModpackInfo;
import top.vmctcn.vmtu.libraries.modpack.info.api.ModpackInfoHelper;
import top.vmctcn.vmtu.libraries.modpack.metadata.api.ModpackMetadata;
import top.vmctcn.vmtu.libraries.modpack.metadata.api.ModpackMetadataReader;
import top.vmctcn.vmtu.libraries.resourcepack.ExtraResourcePackInfo;
import top.vmctcn.vmtu.libraries.resourcepack.ResourcePackInfo;
import top.vmctcn.vmtu.libraries.resourcepack.ResourcePackModule;
import top.vmctcn.vmtu.libraries.resourcepack.pack.ResourcePackIndex;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;

import top.vmctcn.vmtu.mod.modpack.updater.VMMetadata;
import top.vmctcn.vmtu.mod.modpack.updater.VMMetadataReader;

import java.nio.file.Path;
import java.util.Objects;

public class VMTranslationUpdateMod {

    @SuppressWarnings({"ConstantConditions"})
    public static void init() {
        String gameVersion = ModPlatform.getInstance().getGameVersion();
        Path gameDir = ModPlatform.getInstance().getGameDir();
        CommonContexts.setGameInfo(gameVersion, gameDir);

        ModConfigs config = ModConfigHelper.getConfig();
        ModpackInfo.Modpack modpack = ModpackInfoHelper.getModpackInfo().getModpack();
        ModpackMetadata modpackMetadata = ModpackMetadataReader.getMetadata();

        syncModpackVersion(modpack, modpackMetadata);
        LanguageUtils.autoSwitchLanguage();

        if (config.misc.devMode || ModPlatform.getInstance().isDevelopmentEnvironment()) {
            logDevInfo(modpack, modpackMetadata);
        }
    }

    public static void autoDownloadAndLoadPack() {
        ModConfigs.ResourcePack resourcePackConfig = ModConfigHelper.getConfig().resourcePack;
        String extraPackName = resourcePackConfig.extraPackName;
        ResourcePackIndex resourcePackIndex = resourcePackConfig.resourcePackIndex;
        int extraPackCustomIndex = resourcePackConfig.extraPackCustomIndex;
        boolean autoDownloadPack = resourcePackConfig.autoDownloadVMTranslationPack;
        boolean autoLoadExtraPack = resourcePackConfig.autoLoadExtraTranslationPack;

        ExtraResourcePackInfo extraResourcePackInfo = new ExtraResourcePackInfo(extraPackName, extraPackCustomIndex, autoLoadExtraPack);
        ResourcePackInfo resourcePackInfo = new ResourcePackInfo(resourcePackIndex, autoDownloadPack);
        new ResourcePackModule(extraResourcePackInfo, resourcePackInfo);
    }

    private static void syncModpackVersion(ModpackInfo.Modpack modpack, ModpackMetadata modpackMetadata) {
        if (modpackMetadata == null || ModpackInfoHelper.isExampleModpackInfo()) {
            return;
        }

        if (!Objects.equals(modpack.getVersion(), modpackMetadata.getModpackVersion())) {
            ModpackInfoHelper.syncModpackVersion(modpackMetadata.getModpackVersion());
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
