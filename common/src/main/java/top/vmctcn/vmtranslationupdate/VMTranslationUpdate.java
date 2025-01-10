package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.vmctcn.vmtranslationupdate.util.GameOptionsUtil;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;
import top.vmctcn.vmtranslationupdate.util.ScreenUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VMTranslationUpdate {
    public static final String MOD_ID = "vmtranslationupdate";
    public static final Logger LOGGER = LoggerFactory.getLogger("VMTranslationUpdateMod");
    public static final Path OPTIONS_FILE = Paths.get(MinecraftClient.getInstance().runDirectory.toString(), "options.txt");

    public static void init() {
        ScreenUtil.checkModsLoaded();

        if (ModConfigUtil.getConfig().autoSwitchLanguage && ModConfigUtil.getConfig().switchLanguage != null) {
            try {
                GameOptionsUtil.createInitFile(OPTIONS_FILE.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}