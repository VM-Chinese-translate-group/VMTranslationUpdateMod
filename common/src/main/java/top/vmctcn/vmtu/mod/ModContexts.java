package top.vmctcn.vmtu.mod;

//? if >=1.18.2 {
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//?} else {
/*import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
*///?}

public class ModContexts {
    public static final String MOD_ID = "vmtranslationupdate";
    //? if >=1.18.2 {
    public static final Logger LOGGER = LoggerFactory.getLogger("VMTranslationUpdateMod");
    //?} else {
    /*public static final Logger LOGGER = LogManager.getLogger("VMTranslationUpdateMod");
     *///?}

    public static boolean i18nUpdateModLoaded = isCoreModClassLoaded("i18nupdatemod.I18nUpdateMod");
    public static boolean vaultPatcherLoaded = isCoreModClassLoaded("me.fengming.vaultpatcher_asm.VaultPatcher");
    public static boolean textureLocaleRedirectorLoaded = ModPlatform.isModLoaded("texturelocaleredirector");

    public static boolean isCoreModClassLoaded(String className) {
        try {
            Class.forName(className);
            return true; // 类存在，coremod已加载
        } catch (ClassNotFoundException e) {
            return false; // 类不存在
        }
    }
}