package top.vmctcn.vmtu.mod;

import net.minecraft.network.chat.Component;
import top.vmctcn.vmtu.multiversion.Texts;

public class ModContexts {
    public static class ModPresent {
        public static boolean i18nUpdateMod = isCoreModClassLoaded("i18nupdatemod.I18nUpdateMod");
        public static boolean vaultPatcher = isCoreModClassLoaded("me.fengming.vaultpatcher_asm.VaultPatcher");
        public static boolean textureLocaleRedirector = ModPlatform.INSTANCE.isModLoaded("texturelocaleredirector");

        public static boolean isCoreModClassLoaded(String className) {
            try {
                Class.forName(className);
                return true; // 类存在，coremod已加载
            } catch (ClassNotFoundException e) {
                return false; // 类不存在
            }
        }
    }

    public static class ScreenTexts {
        public static final Component downloadButton = Texts.translatable("mco.brokenworld.download");
        public static final Component ignoreButton = Texts.translatable("selectWorld.backupJoinSkipButton");
    }
}