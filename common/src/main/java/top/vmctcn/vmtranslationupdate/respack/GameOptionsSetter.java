package top.vmctcn.vmtranslationupdate.respack;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.modpack.ModpackInfoReader;
import top.vmctcn.vmtranslationupdate.config.ModConfigHelper;
import top.vmctcn.vmtucore.ModPlatform;
import top.vmctcn.vmtucore.VMTUCore;
import top.vmctcn.vmtucore.respack.GameOptionsWriter;

import java.io.IOException;
import java.nio.file.Path;

public class GameOptionsSetter {
    public static void init(Path gamePath) {
        if (ModConfigHelper.getConfig().autoSwitchLanguage) {
            try {
                GameOptionsWriter writer = new GameOptionsWriter(gamePath.resolve("options.txt"));
                var lang = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage();
                writer.switchLanguage(LanguageHelper.getFixedLanguage(lang));
                VMTranslationUpdate.LOGGER.info("Successful to switch language: {}", lang);
            } catch (IOException e) {
                VMTranslationUpdate.LOGGER.error("Failed to switch language: ", e);
            }
        }

        if (ModConfigHelper.getConfig().autoDownloadVMTranslationPack) {
            var gameVersion = ModPlatform.INSTANCE.getGameVersion();
            var resPackSource = ModConfigHelper.getConfig().translationPackSource;
            var resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
            VMTUCore.init(gamePath, gameVersion, resPackName, resPackSource.getUrl());
        }
    }
}
