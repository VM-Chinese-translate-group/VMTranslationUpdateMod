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
    public static void init(Path gamePath) {
        if (ModConfigHelper.getConfig().misc.autoSwitchLanguage && ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage() != null) {
            try {
                GameOptionsWriter writer = new GameOptionsWriter(gamePath.resolve("options.txt"));
                String lang = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage();
                writer.switchLanguage(LanguageHelper.getFixedLanguage(lang));
            } catch (Exception e) {
                VMTranslationUpdate.LOGGER.warn("Failed to switch language: ", e);
            }
        }

        if (ModConfigHelper.getConfig().misc.autoDownloadVMTranslationPack) {
            String gameVersion = ModPlatform.INSTANCE.getGameVersion();
            String extraPackName = ModConfigHelper.getConfig().extraTranslationPack.packName;
            ExtraPackIndex extraPackIndex = ModConfigHelper.getConfig().extraTranslationPack.index;
            int extraPackCustomIndex = ModConfigHelper.getConfig().extraTranslationPack.customIndex;
            String resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
            VMTUCore.init(gamePath, gameVersion, resPackName, extraPackName, extraPackIndex, extraPackCustomIndex);
        }
    }
}
