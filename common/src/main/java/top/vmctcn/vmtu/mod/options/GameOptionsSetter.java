package top.vmctcn.vmtu.mod.options;

import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.pack.GameOptionsWriter;
import top.vmctcn.vmtu.core.pack.PackSource;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.mod.modpack.ModpackInfoReader;

import java.nio.file.Path;

public class GameOptionsSetter {
    public static void init(Path gamePath) {
        if (ModConfigHelper.getConfig().autoSwitchLanguage) {
            try {
                GameOptionsWriter writer = new GameOptionsWriter(gamePath.resolve("options.txt"));
                String lang = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage();
                writer.switchLanguage(LanguageHelper.getFixedLanguage(lang));
                VMTranslationUpdate.LOGGER.info("Successful to switch language: {}", lang);
            } catch (Exception e) {
                VMTranslationUpdate.LOGGER.error("Failed to switch language: ", e);
            }
        }

        if (ModConfigHelper.getConfig().autoDownloadVMTranslationPack) {
            String gameVersion = ModPlatform.INSTANCE.getGameVersion();
            PackSource resPackSource = ModConfigHelper.getConfig().translationPackSource;
            String resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
            VMTUCore.init(gamePath, gameVersion, resPackName, resPackSource);
        }
    }
}
