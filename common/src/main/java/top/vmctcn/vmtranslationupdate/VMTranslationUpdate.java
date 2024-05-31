package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.vmctcn.vmtranslationupdate.util.ScreenUtil;
import top.vmctcn.vmtranslationupdate.util.*;

import java.util.Random;

public class VMTranslationUpdate {
    public static Random random = new Random();
    public static int tickCounter;
    public static final String MODNAME = "VMTranslationUpdate";
    public static final String MOD_ID = "vmtranslationupdate";
    static MinecraftClient client = MinecraftClient.getInstance();
    public static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);
    private static final boolean isStenographerLoaded = ModPlatform.isModLoaded("stenographer"); // Stenographer 兼容

    public static void init() {
        ScreenUtil.checkModsLoaded();

        if (ModConfigUtil.getConfig().autoSwitchLanguage && !isStenographerLoaded && ModConfigUtil.getConfig().switchLanguage != null) {
            client.options.language = ModConfigUtil.getConfig().switchLanguage;
        }

        if (ModConfigUtil.getConfig().autoDownloadVMTranslationPack) {
            PackDownloadUtil.downloadResPack();
        }
    }
}
