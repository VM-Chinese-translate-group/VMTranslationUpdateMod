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
        if (ModConfigHelper.getConfig().autoSwitchLanguage && ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage() != null) {
            try {
                GameOptionsWriter writer = new GameOptionsWriter(gamePath.resolve("options.txt"));
                String lang = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage();
                writer.switchLanguage(LanguageHelper.getFixedLanguage(lang));
            } catch (IOException e) {
                VMTranslationUpdate.LOGGER.warn("Failed to switch language: ", e);
            }
        }

        if (ModConfigHelper.getConfig().autoDownloadVMTranslationPack) {
            String gameVersion = ModPlatform.INSTANCE.getGameVersion();
            ResPackSource resPackSource = ModConfigHelper.getConfig().translationPackSource;
            VMTUCore.init(gamePath, gameVersion, ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName(), resPackSource.getUrl());
        }
    }
}
