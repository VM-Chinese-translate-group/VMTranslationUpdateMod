package top.vmctcn.vmtu.mod;

import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import top.vmctcn.vmtu.multiversion.Texts;

public class ModContexts {
    public static final String MOD_ID = "vmtranslationupdate";
    public static final String MOD_NAME = "VMTranslationUpdateMod";
    public static Logger LOGGER = LogManager.getLogger("VMTranslationUpdateMod");

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

    public static @NotNull Text getTranslatableText(String type, String... path) {
        return Texts.translatable(getTranslationKey(type, path));
    }
}
