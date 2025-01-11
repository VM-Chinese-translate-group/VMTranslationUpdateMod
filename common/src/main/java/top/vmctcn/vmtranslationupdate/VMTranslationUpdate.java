package top.vmctcn.vmtranslationupdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.vmctcn.vmtranslationupdate.modpack.ModpackInfo;
import top.vmctcn.vmtranslationupdate.modpack.ModpackInfoReader;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;

public class VMTranslationUpdate {
    public static final String MOD_ID = "vmtranslationupdate";
    public static final Logger LOGGER = LoggerFactory.getLogger("VMTranslationUpdateMod");

    public static void init() {
        if (ModConfigUtil.getConfig().testMode) {
            ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
            ModpackInfo.Translation translation = modpack.getTranslation();

            LOGGER.warn("Modpack Name: {}", modpack.getName());
            LOGGER.warn("Modpack Version: {}", modpack.getVersion());
            LOGGER.warn("Modpack Translation URL: {}", translation.getUrl());
            LOGGER.warn("Modpack Translation Update Check URL: {}", translation.getUpdateCheckUrl());
            LOGGER.warn("Modpack Translation Language: {}", translation.getLanguage());
            LOGGER.warn("Modpack Translation Version: {}", translation.getVersion());
            LOGGER.warn("Modpack Translation Resource Pack Name: {}", translation.getResourcePackName());
        }
    }
}