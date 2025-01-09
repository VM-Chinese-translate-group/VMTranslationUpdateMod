package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.MinecraftClient;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;
import top.vmctcn.vmtranslationupdate.util.ScreenUtil;

public class VMTranslationUpdate {
    public static final String MOD_ID = "vmtranslationupdate";
    static MinecraftClient client = MinecraftClient.getInstance();

    public static void init() {
        ScreenUtil.checkModsLoaded();

        if (ModConfigUtil.getConfig().autoSwitchLanguage && ModConfigUtil.getConfig().switchLanguage != null) {
            client.options.language = ModConfigUtil.getConfig().switchLanguage;
        }
    }
}