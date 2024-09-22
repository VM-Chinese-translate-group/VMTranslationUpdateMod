package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;
import top.vmctcn.vmtranslationupdate.screen.SuggestModScreen;

public class ScreenUtil {
    public static boolean i18nUpdateModPresent = false;
    public static boolean vaultPatcherPresent = false;
    public static boolean firstTitleScreenShown = false;
    public static final Text downloadButtonText = Text.translatable("vmtranslationupdate.warn.download.button");
    public static final Text quitButtonText = Text.translatable("vmtranslationupdate.warn.quit.button");

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        MinecraftClient.getInstance().setScreen(new SuggestModScreen(screen));

        firstTitleScreenShown = true;
    }

    public static void checkModsLoaded() {
        i18nUpdateModPresent = isModPresent("i18nupdatemod.I18nUpdateMod");
        vaultPatcherPresent = isModPresent("me.fengming.vaultpatcher_asm.VaultPatcher");
    }

    private static boolean isModPresent(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void openUrlOnScreen(MinecraftClient client, Screen screen, String url) {
        if (StringUtils.isNotBlank(url) && client != null) {
            client.setScreen(new ConfirmLinkScreen(yes -> {
                if (yes) {
                    Util.getOperatingSystem().open(url);
                }
                client.setScreen(screen);
            }, url, true));
        }
    }

    public static void resetShaderColor(DrawContext context) {
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static Text getSuggestScreenTitle() {
        return generateWarningText("vmtranslationupdate.warn.title");
    }

    public static Text getSuggestScreenText() {
        return generateWarningText("vmtranslationupdate.warn.text");
    }

    private static Text generateWarningText(String key) {
        String missingMods = getMissingModsText();
        if (!missingMods.isEmpty()) {
            return Text.translatable(key, missingMods);
        }
        return Text.empty();
    }

    private static String getMissingModsText() {
        if (ModConfigUtil.getConfig().i18nUpdateModCheck && ModConfigUtil.getConfig().vaultPatcherCheck) {
            if (!i18nUpdateModPresent && !vaultPatcherPresent) {
                return "I18nUpdateMod & VaultPatcher";
            } else if (!i18nUpdateModPresent) {
                return "I18nUpdateMod";
            } else if (!vaultPatcherPresent) {
                return "VaultPatcher";
            }
        }
        return "";
    }
}
