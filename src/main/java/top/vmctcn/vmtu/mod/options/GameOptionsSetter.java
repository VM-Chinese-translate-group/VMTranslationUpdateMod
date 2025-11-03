package top.vmctcn.vmtu.mod.options;

import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.pack.GameOptionsWriter;
import top.vmctcn.vmtu.core.pack.ResourcePackIndex;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;

import java.nio.file.Path;

public class GameOptionsSetter {
    private static final Path gamePath = ModPlatform.getGameDir();

    public static void autoSwitchLanguage() {
        if (ModConfigs.autoSwitchLanguage && ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage() != null) {
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
        boolean autoDownloadPack = ModConfigs.autoDownloadVMTranslationPack;
        boolean autoLoadExtraPack = ModConfigs.autoLoadExtraTranslationPack;
        String gameVersion = ModPlatform.getGameVersion();
        String extraPackName = ModConfigs.extraPackName;
        ResourcePackIndex resourcePackIndex = ModConfigs.resourcePackIndex;
        int extraPackCustomIndex = ModConfigs.extraPackCustomIndex;
        String resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
        VMTUCore.init(gamePath, gameVersion, resPackName, extraPackName, resourcePackIndex, extraPackCustomIndex, autoDownloadPack, autoLoadExtraPack);
    }
}
