package top.vmctcn.vmtu.mod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.GameEventHelper;
import top.vmctcn.vmtu.mod.modpack.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.VersionChecker;
import top.vmctcn.vmtu.mod.screen.SuggestModScreen;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(ServerPlayerEntity player) {
        String localVersion = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getVersion();
        String onlineVersion = VersionChecker.getOnlineVersion();

        if (ModConfigHelper.getConfig().testMode) {
            ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
            ModpackInfo.Translation translation = modpack.getTranslation();

            player.sendMessage(new LiteralText("==================== VMTU testMode ===================="), false);
            player.sendMessage(new LiteralText("Modpack Name: " + modpack.getName()), false);
            player.sendMessage(new LiteralText("Modpack Version: " + modpack.getVersion()), false);
            player.sendMessage(new LiteralText("Modpack Translation URL:§b " + translation.getUrl()), false);
            player.sendMessage(new LiteralText("Modpack Translation Update Check URL:§b " + translation.getUpdateCheckUrl()), false);
            player.sendMessage(new LiteralText("Modpack Translation Language: " + translation.getLanguage()), false);
            player.sendMessage(new LiteralText("Modpack Translation Version: " + translation.getVersion()), false);
            player.sendMessage(new LiteralText("Modpack Translation Resource Pack Name: " + translation.getResourcePackName()), false);
        }

        if (ModConfigHelper.getConfig().checkModPackTranslationUpdate) {
            if (onlineVersion.isEmpty()) {
                player.sendMessage(new TranslatableText("vmtranslationupdate.message.error"), false);
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            if (!localVersion.equals(onlineVersion)) {
                String updateUrl = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getUrl();
                player.sendMessage(new TranslatableText("vmtranslationupdate.message.update", localVersion, onlineVersion),false);

                Text message = new TranslatableText("vmtranslationupdate.message.update2")
                        .append(new TranslatableText(updateUrl)
                                .setStyle(Style.EMPTY
                                        .withClickEvent(GameEventHelper.clickOpenUrl(updateUrl))
                                        .withHoverEvent(GameEventHelper.hoverShowText(new TranslatableText("vmtranslationupdate.message.hover")))
                                        .withColor(Formatting.AQUA)
                                ))
                        .append(new TranslatableText("vmtranslationupdate.message.update3"));
                player.sendMessage(message, false);
            }
        }
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        String language = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();

        if ("zh_cn".equals(language)) {
            boolean needI18n = ModConfigHelper.getConfig().i18nUpdateModCheck && !SuggestModScreen.i18nUpdateModPresent;
            boolean needVP = ModConfigHelper.getConfig().vaultPatcherCheck && !SuggestModScreen.vaultPatcherPresent;

            // 只要有任何一个模组需要提示，就显示屏幕
            if (needI18n || needVP) {
                MinecraftClient.getInstance().openScreen(new SuggestModScreen(screen));
            }
        }

        firstTitleScreenShown = true;
    }
}