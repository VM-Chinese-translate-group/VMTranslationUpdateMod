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
import top.vmctcn.vmtu.mod.screen.I18nUpdateNotInstallScreen;
//? if >= 1.20.1 {
import top.vmctcn.vmtu.mod.screen.TextureLocaleRedirectorNotInstallScreen;
//?}
import top.vmctcn.vmtu.mod.screen.VaultPatcherNotInstallScreen;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;
import top.vmctcn.vmtu.mod.modpack.updater.OnlineVersion;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.updater.VersionChecker;
import top.vmctcn.vmtu.multiversion.GameEvents;
import top.vmctcn.vmtu.multiversion.Messages;
import top.vmctcn.vmtu.multiversion.Texts;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(Player player) {
        if (player == null) return;

        LanguageManager languageManager = Minecraft.getInstance().getLanguageManager();
        //? if >= 1.20.1 {
        String language = languageManager.getSelected();
         //?} else if <=1.19.2 {
        /*String language = languageManager.getSelected().getCode();
        *///?}

        ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackInfo.Translation translation = modpack.getTranslation();

        String localTranslationVersion = translation.getVersion();
        String localModpackVersion = modpack.getVersion();

        OnlineVersion onlineVersion = VersionChecker.getOnlineVersion(modpack);

        if (ModConfigHelper.getConfig().misc.devMode) {
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

        if (!translation.getLanguage().equals(language) && LanguageUtils.isChineseLanguage()) {
            Messages.displayClientMessage(player, Texts.translatable("vmtu.message.language.not_support", translation.getLanguage()));
        }

        if (ModConfigHelper.getConfig().misc.checkModPackTranslationUpdate) {
            if (!onlineVersion.isValid()) {
                Messages.displayClientMessage(player, Texts.translatable("vmtu.message.update.error"));
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            boolean translationUpdateNeeded = !localTranslationVersion.equals(onlineVersion.translationVersion());
            boolean modpackUpdateNeeded = !onlineVersion.modpackVersion().isEmpty() && !localModpackVersion.equals(onlineVersion.modpackVersion());
            Component coloredLocalVer = Texts.literal(localTranslationVersion).withStyle(s -> s.withColor(ChatFormatting.YELLOW));
            Component coloredOnlineVer = Texts.literal(onlineVersion.translationVersion()).withStyle(s -> s.withColor(ChatFormatting.YELLOW));

            if (translationUpdateNeeded) {
                Messages.displayClientMessage(player, Texts.translatable("vmtu.message.update.new_version.text.part1", coloredLocalVer, coloredOnlineVer));
                String updateUrl = translation.getUrl();
                Component message = Texts.translatable("vmtu.message.update.new_version.text.part2")
                        .append(Texts.translatable("vmtu.message.update.new_version.text.download_link")
                                .setStyle(Style.EMPTY
                                        .withClickEvent(GameEvents.clickOpenUrl(updateUrl))
                                        .withHoverEvent(GameEvents.hoverShowText(Texts.translatable("vmtu.message.update.new_version.text.download_hover")))
                                        .withColor(ChatFormatting.AQUA)
                                ))
                        .append(Texts.translatable("vmtu.message.update.new_version.text.part3"));
                Messages.displayClientMessage(player, message);

                if (modpackUpdateNeeded){
                    Component coloredLocalModpackVer = Texts.literal(localModpackVersion).withStyle(s -> s.withColor(ChatFormatting.YELLOW));
                    Component coloredOnlineModpackVer = Texts.literal(onlineVersion.modpackVersion()).withStyle(s -> s.withColor(ChatFormatting.YELLOW));
                    Messages.displayClientMessage(player, Texts.translatable("vmtu.message.update.modpack_version.error"));
                    Messages.displayClientMessage(player, Texts.translatable("vmtu.message.update.modpack_version.error.hint", coloredLocalModpackVer, coloredOnlineModpackVer));
                }
            }
        }
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        boolean needI18n = ModConfigHelper.getConfig().modInstallCheck.i18nUpdateMod && !ModContexts.ModPresent.i18nUpdateMod;
        boolean needVP = ModConfigHelper.getConfig().modInstallCheck.vaultPatcher && !ModContexts.ModPresent.vaultPatcher;
        boolean needTLR = ModConfigHelper.getConfig().modInstallCheck.textureLocaleRedirector && !ModContexts.ModPresent.textureLocaleRedirector;

        if (needI18n) {
            Minecraft.getInstance().setScreen(new I18nUpdateNotInstallScreen(screen));
        } else if (needVP) {
            Minecraft.getInstance().setScreen(new VaultPatcherNotInstallScreen(screen));
            //? if >=1.20.1 {
        } else if (needTLR) {
            Minecraft.getInstance().setScreen(new TextureLocaleRedirectorNotInstallScreen(screen));
            //?}
        }

        firstTitleScreenShown = true;
    }
}
