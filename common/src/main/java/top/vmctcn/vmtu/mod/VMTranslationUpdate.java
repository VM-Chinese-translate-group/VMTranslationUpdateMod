package top.vmctcn.vmtu.mod;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.modpack.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.ModpackInfoReader;

import java.util.Set;

public class VMTranslationUpdate {
    public static final String MOD_ID = "vmtranslationupdate";
    public static final Logger LOGGER = LoggerFactory.getLogger("VMTranslationUpdateMod");
    public static boolean LANG_RELOAD = false;

    public static void init() {
        if (ModConfigHelper.getConfig().testMode) {
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

    public static boolean isChineseLanguage() {
        String language = MinecraftClient.getInstance().getLanguageManager().getLanguage();
        Set<String> chineseLangs = Set.of("zh_cn", "zh_tw", "zh_hk", "lzh");
        return chineseLangs.contains(language);
    }
}