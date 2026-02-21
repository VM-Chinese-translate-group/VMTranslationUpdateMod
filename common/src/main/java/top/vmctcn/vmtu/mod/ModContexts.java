package top.vmctcn.vmtu.mod;

import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import top.vmctcn.vmtu.multiversion.Texts;
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

    public static boolean isModClassLoaded(String className) {
        try {
            Class.forName(className);
            return true; // 类存在，mod已加载
        } catch (ClassNotFoundException e) {
            return false; // 类不存在
        }
    }

    public static @NotNull String getTranslationKey(String type, String... path) {
        return type + ".vmtu." + String.join(".", path);
    }

    public static @NotNull MutableComponent getTranslatableText(String type, String... path) {
        return Texts.translatable(getTranslationKey(type, path));
    }
}