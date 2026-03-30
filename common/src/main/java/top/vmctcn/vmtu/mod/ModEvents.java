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
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.screen.MissingModScreen;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.updater.OnlineVersion;
import top.vmctcn.vmtu.mod.modpack.updater.VersionChecker;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;
import top.vmctcn.vmtu.mod.utils.RequiredMods;
import top.vmctcn.vmtu.multiversion.GameEvents;
import top.vmctcn.vmtu.multiversion.Messages;
import top.vmctcn.vmtu.multiversion.Texts;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
    public static ModpackInfo.Translation translation = modpack.getTranslation();
    public static OnlineVersion onlineVersion = VersionChecker.getOnlineVersion(modpack);

    public static void playerJoinEvent(Player player) {
        if (player == null) {
            return;
        }

        ModConfigs config = ModConfigHelper.getConfig();
        LanguageManager languageManager = Minecraft.getInstance().getLanguageManager();
        //? if >= 1.20.1 {
        String language = languageManager.getSelected();
         //?} else if <=1.19.2 {
        /*String language = languageManager.getSelected().getCode();
        *///?}

        String localTranslationVersion = translation.getVersion();
        String localModpackVersion = modpack.getVersion();

        if (shouldShowDevelopmentInfo(config)) {
            showDevelopmentInfo(player, modpack, translation, onlineVersion);
        }

        if (translation.getLanguage() != null && !translation.getLanguage().equals(language) && LanguageUtils.isChineseLanguage()) {
            Messages.displayClientMessage(player, Texts.translatable(ModContexts.getTranslationKey("message", "supported_language"), translation.getLanguage()));
        }

        if (config.misc.checkModPackTranslationUpdate) {
            if (!onlineVersion.isValid()) {
                Messages.displayClientMessage(player, ModContexts.getTranslatableText("message", "update_checker", "error"));
                ModContexts.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            boolean translationUpdateNeeded = !localTranslationVersion.equals(onlineVersion.translationVersion());
            boolean modpackUpdateNeeded = !onlineVersion.modpackVersion().isEmpty() && !localModpackVersion.equals(onlineVersion.modpackVersion());

            if (!ModpackInfoReader.isExampleModpackInfo()) {
                if (translationUpdateNeeded) {
                    checkTranslationUpdateCommand(player);
                    if (modpackUpdateNeeded){
                        checkModpackUpdateCommand(player);
                    }
                }
            }
        }
    }

    public static void checkModpackUpdateCommand(Player player) {
        Component coloredLocalModpackVer = Texts.literal(translation.getVersion()).withStyle(s -> s.withColor(ChatFormatting.YELLOW));
        Component coloredOnlineModpackVer = Texts.literal(onlineVersion.modpackVersion()).withStyle(s -> s.withColor(ChatFormatting.YELLOW));
        Messages.displayClientMessage(player, ModContexts.getTranslatableText("message", "update_checker", "modpack", "line1"));
        Messages.displayClientMessage(player, Texts.translatable(ModContexts.getTranslationKey("message", "update_checker", "modpack", "line2"), coloredLocalModpackVer, coloredOnlineModpackVer));
    }

    public static void checkTranslationUpdateCommand(Player player) {
        Component coloredLocalVer = Texts.literal(translation.getVersion()).withStyle(style -> style.withColor(ChatFormatting.YELLOW));
        Component coloredOnlineVer = Texts.literal(onlineVersion.translationVersion()).withStyle(style -> style.withColor(ChatFormatting.YELLOW));
        Messages.displayClientMessage(player, Texts.translatable(ModContexts.getTranslationKey("message", "update_checker", "translation", "line1"), coloredLocalVer, coloredOnlineVer));
        String updateUrl = translation.getUrl();
        Component message = ModContexts.getTranslatableText("message", "update_checker", "translation", "line2")
                .append(ModContexts.getTranslatableText("message", "update_checker", "translation", "link"))
                .withStyle(Style.EMPTY
                        .withClickEvent(GameEvents.clickOpenUrl(updateUrl))
                        .withHoverEvent(GameEvents.hoverShowText(ModContexts.getTranslatableText("message", "update_checker", "translation", "link", "tooltip")))
                        .withColor(ChatFormatting.AQUA))
                .append(ModContexts.getTranslatableText("message", "update_checker", "translation", "line3"));
        Messages.displayClientMessage(player, message);
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();

        if (!RequiredMods.isAllLoaded()) {
            minecraft.setScreen(new MissingModScreen(RequiredMods.getAllMissing(), screen));
        }

        firstTitleScreenShown = true;
    }

    private static boolean shouldShowDevelopmentInfo(ModConfigs config) {
        return config.misc.devMode || ModPlatform.getInstance().isDevelopmentEnvironment();
    }

    @SuppressWarnings("deprecation")
    private static void showDevelopmentInfo(Player player, ModpackInfo.Modpack modpack, ModpackInfo.Translation translation, OnlineVersion onlineVersion) {
        Messages.displayClientMessage(player, Texts.literal("=================== VMTU Dev Mode ===================="));
        Messages.displayClientMessage(player, Texts.literal("Modpack Name: " + modpack.getName()));
        Messages.displayClientMessage(player, Texts.literal("Modpack Version: " + modpack.getVersion()));
        Messages.displayClientMessage(player, urlText("Modpack Translation URL:", translation.getUrl()));
        if (translation.getUpdateCheckUrl() != null) {
            Messages.displayClientMessage(player, urlText("Modpack Translation Update Check URL: ", translation.getUpdateCheckUrl()));
        }
        Messages.displayClientMessage(player, Texts.literal("Modpack Translation Language: " + translation.getLanguage()));
        Messages.displayClientMessage(player, Texts.literal("Modpack Translation Version: " + translation.getVersion()));
        if (translation.getResourcePackName() != null) {
            Messages.displayClientMessage(player, Texts.literal("Translation Resource Pack Name: " + translation.getResourcePackName()));
        }
        Messages.displayClientMessage(player, Texts.literal("Online Translation Version: " + onlineVersion.translationVersion()));
        Messages.displayClientMessage(player, Texts.literal("Online Modpack Version: " + onlineVersion.modpackVersion()));
        Messages.displayClientMessage(player, Texts.literal("====================================================="));
    }

    private static Component urlText(String label, String url) {
        return Texts.literal(label)
                .append(Texts.literal(url)
                        .withStyle(Style.EMPTY
                                .withClickEvent(GameEvents.clickOpenUrl(url))
                                .withColor(ChatFormatting.AQUA)
                        ));
    }
}
