package top.vmctcn.vmtu.mod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.GameEventHelper;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.mod.modpack.OnlineVersion;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.VersionChecker;
import top.vmctcn.vmtu.mod.screen.SuggestModScreen;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(PlayerEntity player) {
        if (player == null) return;

        LanguageManager languageManager = MinecraftClient.getInstance().getLanguageManager();

        ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackInfo.Translation translation = modpack.getTranslation();

        String localTranslationVersion = translation.getVersion();
        String localModpackVersion = modpack.getVersion();

        OnlineVersion onlineVersion = VersionChecker.getOnlineVersion();

        if (ModConfigHelper.getConfig().devMode) {
            player.sendMessage(Text.literal("==================== VMTU Dev Mode ===================="), false);
            player.sendMessage(Text.literal("Modpack Name: " + modpack.getName()), false);
            player.sendMessage(Text.literal("Modpack Version: " + modpack.getVersion()), false);
            player.sendMessage(Text.literal("Modpack Translation URL:§b " + translation.getUrl()), false);
            if (translation.getUpdateCheckUrl() != null) {
                player.sendMessage(Text.literal("Modpack Translation Update Check URL:§b " + translation.getUpdateCheckUrl()), false);
            }
            player.sendMessage(Text.literal("Modpack Translation Language: " + translation.getLanguage()), false);
            player.sendMessage(Text.literal("Modpack Translation Version: " + translation.getVersion()), false);
            player.sendMessage(Text.literal("Modpack Translation Resource Pack Name: " + translation.getResourcePackName()), false);
            player.sendMessage(Text.literal("Online Translation Version: " + onlineVersion.translationVersion()), false);
            player.sendMessage(Text.literal("Online Modpack Version: " + onlineVersion.modpackVersion()), false);
            player.sendMessage(Text.literal("======================================================="), false);
        }

        if (!translation.getLanguage().equals(languageManager.getLanguage().getCode()) && LanguageHelper.isChineseLanguage()) {
            player.sendMessage(Text.translatable("vmtranslationupdate.message.language_not_support", translation.getLanguage()), false);
        }

        if (ModConfigHelper.getConfig().checkModPackTranslationUpdate) {
            if (!onlineVersion.isValid()) {
                player.sendMessage(Text.translatable("vmtranslationupdate.message.update.error"), false);
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            boolean translationUpdateNeeded = !localTranslationVersion.equals(onlineVersion.translationVersion());
            boolean modpackUpdateNeeded = !onlineVersion.modpackVersion().isEmpty() && !localModpackVersion.equals(onlineVersion.modpackVersion());
            Text coloredLocalVer = Text.literal(localTranslationVersion).styled(s -> s.withColor(Formatting.YELLOW));
            Text coloredOnlineVer = Text.literal(onlineVersion.translationVersion()).styled(s -> s.withColor(Formatting.YELLOW));

            if (translationUpdateNeeded) {
                player.sendMessage(Text.translatable("vmtranslationupdate.message.update.new_version.text_part1", coloredLocalVer, coloredOnlineVer), false);
                String updateUrl = translation.getUrl();
                Text message = Text.translatable("vmtranslationupdate.message.update.new_version.text_part2")
                        .append(Text.translatable(updateUrl)
                                .setStyle(Style.EMPTY
                                        .withClickEvent(GameEventHelper.clickOpenUrl(updateUrl))
                                        .withHoverEvent(GameEventHelper.hoverShowText(Text.translatable("vmtranslationupdate.message.update.new_version.download_hover")))
                                        .withColor(Formatting.AQUA)
                                ))
                        .append(Text.translatable("vmtranslationupdate.message.update.new_version.text_part3"));
                player.sendMessage(message, false);

                if (modpackUpdateNeeded){
                    Text coloredLocalModpackVer = Text.literal(localModpackVersion).styled(s -> s.withColor(Formatting.YELLOW));
                    Text coloredOnlineModpackVer = Text.literal(onlineVersion.modpackVersion()).styled(s -> s.withColor(Formatting.YELLOW));
                    player.sendMessage(Text.translatable("vmtranslationupdate.message.update.modpack_version_error"), false);
                    player.sendMessage(Text.translatable("vmtranslationupdate.message.update.modpack_version_error.hint", coloredLocalModpackVer, coloredOnlineModpackVer), false);
                }
            }
        }
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        boolean needI18n = ModConfigHelper.getConfig().i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod;
        boolean needVP = ModConfigHelper.getConfig().vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher;

        // 只要有任何一个模组需要提示，就显示屏幕
        if (needI18n || needVP) {
            MinecraftClient.getInstance().setScreen(new SuggestModScreen(screen));
        }

        firstTitleScreenShown = true;
    }
}
