package top.vmctcn.vmtu.mod.options;

import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.pack.ExtraPackIndex;
import top.vmctcn.vmtu.core.pack.GameOptionsWriter;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;

import java.nio.file.Path;

public class GameOptionsSetter {
    private static final Path gamePath = ModPlatform.INSTANCE.getGameDir();

    public static void autoSwitchLanguage() {
        if (ModConfigHelper.getConfig().autoSwitchLanguage && ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage() != null) {
            try {
                GameOptionsWriter writer = new GameOptionsWriter(gamePath.resolve("options.txt"));
                String lang = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage();
                writer.switchLanguage(LanguageHelper.getFixedLanguage(lang));
            } catch (Exception e) {
                VMTranslationUpdate.LOGGER.warn("Failed to switch language: ", e);
            }
        }
    }

    public static void autoDownloadAndLoadPack() {
        boolean autoDownloadPack = ModConfigHelper.getConfig().autoDownloadVMTranslationPack;
        boolean autoLoadExtraPack = ModConfigHelper.getConfig().autoLoadExtraTranslationPack;
        String gameVersion = ModPlatform.INSTANCE.getGameVersion();
        String extraPackName = ModConfigHelper.getConfig().extraPackName;
        ExtraPackIndex extraPackIndex = ModConfigHelper.getConfig().extraPackIndex;
        int extraPackCustomIndex = ModConfigHelper.getConfig().extraPackCustomIndex;
        String resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
        VMTUCore.init(gamePath, gameVersion, resPackName, extraPackName, extraPackIndex, extraPackCustomIndex, autoDownloadPack, autoLoadExtraPack);
    }
}
