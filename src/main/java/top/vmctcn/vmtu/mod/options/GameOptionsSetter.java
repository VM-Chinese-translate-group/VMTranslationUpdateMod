package top.vmctcn.vmtu.mod.options;

import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import top.vmctcn.vmtu.core.VMTUCore;
import top.vmctcn.vmtu.core.pack.PackSource;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;

import java.nio.file.Path;

public class GameOptionsSetter {

    public static void setLanguage() {
        if (ModConfigs.autoSwitchLanguage && ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage() != null) {
            Minecraft mc = Minecraft.getInstance();
            GameOptions gameSettings = mc.options;

            String lang = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getLanguage();
            String fixedLang = LanguageHelper.getFixedLanguage(lang);

            if (!gameSettings.language.equals(fixedLang)) {
                mc.getLanguageManager().setLanguage(mc.getLanguageManager().getLanguage(fixedLang));
                gameSettings.language = fixedLang;
            }
        }
    }

    public static void setResourcePack() {
        if (ModConfigs.autoDownloadVMTranslationPack) {
            Path gamePath = ModPlatform.getGameDir();
            String gameVersion = ModPlatform.getGameVersion();
            String resPackName = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getResourcePackName();
            VMTUCore.init(gamePath, gameVersion, resPackName, PackSource.GITEE);
        }
    }
}
