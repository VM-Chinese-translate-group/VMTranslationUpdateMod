package top.vmctcn.vmtu.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.text.Formatting;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import top.vmctcn.vmtu.mod.modpack.updater.OnlineVersion;
import top.vmctcn.vmtu.mod.modpack.updater.VersionChecker;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.multiversion.GameEvents;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;
import top.vmctcn.vmtu.mod.screen.SuggestModScreen;
import top.vmctcn.vmtu.multiversion.Messages;
import top.vmctcn.vmtu.multiversion.Texts;

public class ModEvents {
    private static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(PlayerEntity player) {
        if (player == null) return;

        LanguageManager languageManager = Minecraft.getInstance().getLanguageManager();

        ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackInfo.Translation translation = modpack.getTranslation();

        String localTranslationVersion = translation.getVersion();
        String localModpackVersion = modpack.getVersion();

        OnlineVersion onlineVersion = VersionChecker.getOnlineVersion();

        if (ModConfigs.devMode) {
            Messages.displayClientMessage(player, Texts.literal("==================== VMTU Dev Mode ===================="));
            Messages.displayClientMessage(player, Texts.literal("Modpack Name: " + modpack.getName()));
            Messages.displayClientMessage(player, Texts.literal("Modpack Version: " + modpack.getVersion()));
            Messages.displayClientMessage(player, Texts.literal("Modpack Translation URL:§b " + translation.getUrl()));
            if (translation.getUpdateCheckUrl() != null) {
                Messages.displayClientMessage(player, Texts.literal("Modpack Translation Update Check URL:§b " + translation.getUpdateCheckUrl()));
            }
            Messages.displayClientMessage(player, Texts.literal("Modpack Translation Language: " + translation.getLanguage()));
            Messages.displayClientMessage(player, Texts.literal("Modpack Translation Version: " + translation.getVersion()));
            Messages.displayClientMessage(player, Texts.literal("Modpack Translation Resource Pack Name: " + translation.getResourcePackName()));
            Messages.displayClientMessage(player, Texts.literal("Online Translation Version: " + onlineVersion.translationVersion()));
            Messages.displayClientMessage(player, Texts.literal("Online Modpack Version: " + onlineVersion.modpackVersion()));
            Messages.displayClientMessage(player, Texts.literal("======================================================="));
        }

        if (!translation.getLanguage().equals(languageManager.getLanguage().getCode()) && LanguageUtils.isChineseLanguage()) {
            Messages.displayClientMessage(player, Texts.translatable("vmtranslationupdate.message.language_not_support", translation.getLanguage()));
        }

        if (ModConfigs.checkModPackTranslationUpdate) {
            if (!onlineVersion.isValid()) {
                Messages.displayClientMessage(player, Texts.translatable("vmtranslationupdate.message.update.error"));
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            boolean translationUpdateNeeded = !localTranslationVersion.equals(onlineVersion.translationVersion());
            boolean modpackUpdateNeeded = !onlineVersion.modpackVersion().isEmpty() && !localModpackVersion.equals(onlineVersion.modpackVersion());
            Text coloredLocalVer = Texts.literal(localTranslationVersion).setStyle(new Style().setColor(Formatting.YELLOW));
            Text coloredOnlineVer = Texts.literal(onlineVersion.translationVersion()).setStyle(new Style().setColor(Formatting.YELLOW));

            if (translationUpdateNeeded) {
                Messages.displayClientMessage(player, Texts.translatable("vmtranslationupdate.message.update.new_version.text_part1", coloredLocalVer.getFormattedString(), coloredOnlineVer.getFormattedString()));
                String updateUrl = translation.getUrl();
                Text message = Texts.translatable("vmtranslationupdate.message.update.new_version.text_part2")
                        .append(Texts.translatable(updateUrl)
                                .setStyle(new Style()
                                        .setClickEvent(GameEvents.clickOpenUrl(updateUrl))
                                        .setHoverEvent(GameEvents.hoverShowText(Texts.translatable("vmtranslationupdate.message.update.new_version.download_hover")))
                                        .setColor(Formatting.AQUA)
                                ))
                        .append(Texts.translatable("vmtranslationupdate.message.update.new_version.text_part3"));
                player.sendMessage(message);

                if (modpackUpdateNeeded){
                    Text coloredLocalModpackVer = Texts.literal(localModpackVersion).setStyle(new Style().setColor(Formatting.YELLOW));
                    Text coloredOnlineModpackVer = Texts.literal(onlineVersion.modpackVersion()).setStyle(new Style().setColor(Formatting.YELLOW));
                    Messages.displayClientMessage(player, Texts.translatable("vmtranslationupdate.message.update.modpack_version_error"));
                    Messages.displayClientMessage(player, Texts.translatable("vmtranslationupdate.message.update.modpack_version_error.hint", coloredLocalModpackVer.getFormattedString(), coloredOnlineModpackVer.getFormattedString()));
                }
            }
        }
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (LanguageUtils.isChineseLanguage()) {
            if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
                return;
            }

            boolean needI18n = ModConfigs.i18nUpdateModCheck && !ModContexts.ModPresent.i18nUpdateMod;
            boolean needVP = ModConfigs.vaultPatcherCheck && !ModContexts.ModPresent.vaultPatcher;

            // 只要有任何一个模组需要提示，就显示屏幕
            if (needI18n || needVP) {
                Minecraft.getInstance().openScreen(new SuggestModScreen(screen));
            }

            firstTitleScreenShown = true;
        }
    }
}