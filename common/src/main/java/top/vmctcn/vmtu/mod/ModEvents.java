package top.vmctcn.vmtu.mod;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.GameEventHelper;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.mod.modpack.updater.OnlineVersion;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.updater.VersionChecker;
import top.vmctcn.vmtu.mod.screen.SuggestModScreen;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(Player player) {
        if (player == null) return;

        LanguageManager languageManager = Minecraft.getInstance().getLanguageManager();

        ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackInfo.Translation translation = modpack.getTranslation();

        String localTranslationVersion = translation.getVersion();
        String localModpackVersion = modpack.getVersion();

        OnlineVersion onlineVersion = VersionChecker.getOnlineVersion(modpack);

        if (ModConfigHelper.getConfig().devMode) {
            player.displayClientMessage(Component.literal("==================== VMTU Dev Mode ===================="), false);
            player.displayClientMessage(Component.literal("Modpack Name: " + modpack.getName()), false);
            player.displayClientMessage(Component.literal("Modpack Version: " + modpack.getVersion()), false);
            player.displayClientMessage(Component.literal("Modpack Translation URL:§b " + translation.getUrl()), false);
            if (translation.getUpdateCheckUrl() != null) {
                player.displayClientMessage(Component.literal("Modpack Translation Update Check URL:§b " + translation.getUpdateCheckUrl()), false);
            }
            player.displayClientMessage(Component.literal("Modpack Translation Language: " + translation.getLanguage()), false);
            player.displayClientMessage(Component.literal("Modpack Translation Version: " + translation.getVersion()), false);
            player.displayClientMessage(Component.literal("Modpack Translation Resource Pack Name: " + translation.getResourcePackName()), false);
            player.displayClientMessage(Component.literal("Online Translation Version: " + onlineVersion.translationVersion()), false);
            player.displayClientMessage(Component.literal("Online Modpack Version: " + onlineVersion.modpackVersion()), false);
            player.displayClientMessage(Component.literal("======================================================="), false);
        }

        if (!translation.getLanguage().equals(languageManager.getSelected()) && LanguageHelper.isChineseLanguage()) {
            player.displayClientMessage(Component.translatable("vmtranslationupdate.message.language_not_support", translation.getLanguage()), false);
        }

        if (ModConfigHelper.getConfig().checkModPackTranslationUpdate) {
            if (!onlineVersion.isValid()) {
                player.displayClientMessage(Component.translatable("vmtranslationupdate.message.update.error"), false);
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            boolean translationUpdateNeeded = !localTranslationVersion.equals(onlineVersion.translationVersion());
            boolean modpackUpdateNeeded = !onlineVersion.modpackVersion().isEmpty() && !localModpackVersion.equals(onlineVersion.modpackVersion());
            Component coloredLocalVer = Component.literal(localTranslationVersion).withStyle(s -> s.withColor(ChatFormatting.YELLOW));
            Component coloredOnlineVer = Component.literal(onlineVersion.translationVersion()).withStyle(s -> s.withColor(ChatFormatting.YELLOW));

            if (translationUpdateNeeded) {
                player.displayClientMessage(Component.translatable("vmtranslationupdate.message.update.new_version.text_part1", coloredLocalVer, coloredOnlineVer), false);
                String updateUrl = translation.getUrl();
                Component message = Component.translatable("vmtranslationupdate.message.update.new_version.text_part2")
                        .append(Component.translatable(updateUrl)
                                .setStyle(Style.EMPTY
                                        .withClickEvent(GameEventHelper.clickOpenUrl(updateUrl))
                                        .withHoverEvent(GameEventHelper.hoverShowText(Component.translatable("vmtranslationupdate.message.update.new_version.download_hover")))
                                        .withColor(ChatFormatting.AQUA)
                                ))
                        .append(Component.translatable("vmtranslationupdate.message.update.new_version.text_part3"));
                player.displayClientMessage(message, false);

                if (modpackUpdateNeeded){
                    Component coloredLocalModpackVer = Component.literal(localModpackVersion).withStyle(s -> s.withColor(ChatFormatting.YELLOW));
                    Component coloredOnlineModpackVer = Component.literal(onlineVersion.modpackVersion()).withStyle(s -> s.withColor(ChatFormatting.YELLOW));
                    player.displayClientMessage(Component.translatable("vmtranslationupdate.message.update.modpack_version_error"), false);
                    player.displayClientMessage(Component.translatable("vmtranslationupdate.message.update.modpack_version_error.hint", coloredLocalModpackVer, coloredOnlineModpackVer), false);
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
            Minecraft.getInstance().setScreen(new SuggestModScreen(screen));
        }

        firstTitleScreenShown = true;
    }
}
