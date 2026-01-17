package top.vmctcn.vmtu.mod;

//? if >=1.18.2 {
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//?} else {
/*import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
*///?}
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
import java.util.ServiceLoader;

public class VMTranslationUpdate {
    public static final String MOD_ID = "vmtranslationupdate";
    //? if >=1.18.2 {
    public static final Logger LOGGER = LoggerFactory.getLogger("VMTranslationUpdateMod");
    //?} else {
    /*public static final Logger LOGGER = LogManager.getLogger("VMTranslationUpdateMod");
    *///?}

    public static <T> T loadService(final Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new AssertionError("No impl found for " + clazz.getPackageName()));
    }

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