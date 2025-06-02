package top.vmctcn.vmtu.mod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.modpack.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.VersionChecker;
import top.vmctcn.vmtu.mod.screen.SuggestModScreen;
import top.vmctcn.vmtu.mod.helper.SuggestScreenHelper;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(ServerPlayerEntity player) {
        String localVersion = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getVersion();
        String onlineVersion = VersionChecker.getOnlineVersion();

        if (ModConfigHelper.getConfig().testMode) {
            ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
            ModpackInfo.Translation translation = modpack.getTranslation();

            player.sendMessage(Text.literal("==================== VMTU testMode ===================="), false);
            player.sendMessage(Text.literal("Modpack Name: " + modpack.getName()), false);
            player.sendMessage(Text.literal("Modpack Version: " + modpack.getVersion()), false);
            player.sendMessage(Text.literal("Modpack Translation URL:§b " + translation.getUrl()), false);
            player.sendMessage(Text.literal("Modpack Translation Update Check URL:§b " + translation.getUpdateCheckUrl()), false);
            player.sendMessage(Text.literal("Modpack Translation Language: " + translation.getLanguage()), false);
            player.sendMessage(Text.literal("Modpack Translation Version: " + translation.getVersion()), false);
            player.sendMessage(Text.literal("Modpack Translation Resource Pack Name: " + translation.getResourcePackName()), false);
        }

        if (ModConfigHelper.getConfig().checkModPackTranslationUpdate) {
            if (onlineVersion.isEmpty()) {
                player.sendMessage(Text.translatable("vmtranslationupdate.message.error"), false);
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            if (!localVersion.equals(onlineVersion)) {
                String updateUrl = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getUrl();
                player.sendMessage(Text.translatable("vmtranslationupdate.message.update", localVersion, onlineVersion));
                Text message = Text.translatable("vmtranslationupdate.message.update2")
                        .append(Text.translatable(updateUrl)
                                .setStyle(Style.EMPTY
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, updateUrl))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("vmtranslationupdate.message.hover")))
                                        .withColor(Formatting.AQUA)
                                ))
                        .append(Text.translatable("vmtranslationupdate.message.update3"));
                player.sendMessage(message);
            }
        }
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        String language = MinecraftClient.getInstance().getLanguageManager().getLanguage();

        if ("zh_cn".equals(language)) {
            boolean needI18n = ModConfigHelper.getConfig().i18nUpdateModCheck && !SuggestScreenHelper.i18nUpdateModPresent;
            boolean needVP = ModConfigHelper.getConfig().vaultPatcherCheck && !SuggestScreenHelper.vaultPatcherPresent;

            // 只要有任何一个模组需要提示，就显示屏幕
            if (needI18n || needVP) {
                MinecraftClient.getInstance().setScreen(new SuggestModScreen(screen));
            }
        }

        firstTitleScreenShown = true;
    }
}
