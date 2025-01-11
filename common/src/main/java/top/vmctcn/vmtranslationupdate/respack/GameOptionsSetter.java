package top.vmctcn.vmtranslationupdate.respack;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.modpack.ModpackInfoReader;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;
import top.vmctcn.vmtucore.ModPlatform;
import top.vmctcn.vmtucore.VMTUCore;
import top.vmctcn.vmtucore.respack.GameOptionsWriter;

import java.io.IOException;
import java.nio.file.Path;

public class GameOptionsSetter {
    public static void init(Path gamePath) {
        if (ModConfigUtil.getConfig().autoSwitchLanguage && ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage() != null) {
            try {
                GameOptionsWriter config = new GameOptionsWriter(gamePath.resolve("options.txt"));
                config.switchLanguage(ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage());
            } catch (IOException e) {
                VMTranslationUpdate.LOGGER.warn("Failed to switch language: ", e);
            }
        }

        if (ModConfigUtil.getConfig().autoDownloadVMTranslationPack) {
            String gameVersion = ModPlatform.INSTANCE.getGameVersion();
            ResPackSource resPackSource = ModConfigUtil.getConfig().translationPackSource;
            VMTUCore.init(gamePath, gameVersion, ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName(), resPackSource.getUrl());
        }
    }
}
