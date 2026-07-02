package top.vmctcn.vmtu.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.text.Formatting;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import top.vmctcn.vmtu.libraries.modpack.info.api.ModpackInfo;
import top.vmctcn.vmtu.libraries.modpack.info.api.ModpackInfoHelper;
import top.vmctcn.vmtu.mod.modpack.updater.OnlineVersion;
import top.vmctcn.vmtu.mod.modpack.updater.VersionChecker;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.screen.MissingModScreen;
import top.vmctcn.vmtu.mod.utils.RequiredMods;
import top.vmctcn.vmtu.multiversion.GameEvents;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;
import top.vmctcn.vmtu.multiversion.Messages;
import top.vmctcn.vmtu.multiversion.Texts;

public class ModEvents {
    private static boolean firstTitleScreenShown = false;

    public static ModpackInfo.Modpack modpack = ModpackInfoHelper.getModpackInfo().getModpack();
    public static ModpackInfo.Translation translation = modpack.getTranslation();
    public static OnlineVersion onlineVersion = VersionChecker.getOnlineVersion(modpack);

    public static void playerJoinEvent(PlayerEntity player) {
        if (player == null) return;

        LanguageManager languageManager = Minecraft.getInstance().getLanguageManager();
        String language = languageManager.getLanguage().getCode();

        String localTranslationVersion = translation.getVersion();
        String localModpackVersion = modpack.getVersion();

        if (ModConfigs.misc.devMode) {
            showDevelopmentInfo(player, modpack, translation, onlineVersion);
        }

        if (translation.getLanguage() != null && !translation.getLanguage().equals(language) && LanguageUtils.isChineseLanguage()) {
            Messages.displayClientMessage(player, Texts.translatable(ModContexts.getTranslationKey("message", "supported_language"), translation.getLanguage()));
        }

        if (ModConfigs.misc.checkModPackTranslationUpdate) {
            if (!onlineVersion.isValid()) {
                Messages.displayClientMessage(player, ModContexts.getTranslatableText("message", "update_checker", "error"));
                ModContexts.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            boolean translationUpdateNeeded = !localTranslationVersion.equals(onlineVersion.translationVersion());
            boolean modpackUpdateNeeded = !onlineVersion.modpackVersion().isEmpty() && !localModpackVersion.equals(onlineVersion.modpackVersion());

            if (!ModpackInfoHelper.isExampleModpackInfo()) {
                if (translationUpdateNeeded) {
                    checkTranslationUpdateCommand(player);
                    if (modpackUpdateNeeded){
                        checkModpackUpdateCommand(player);
                    }
                }
            }
        }
    }

    public static void checkModpackUpdateCommand(PlayerEntity player) {
        Text coloredLocalModpackVer = Texts.literal(translation.getVersion()).setStyle(new Style().setColor(Formatting.YELLOW));
        Text coloredOnlineModpackVer = Texts.literal(onlineVersion.modpackVersion()).setStyle(new Style().setColor(Formatting.YELLOW));
        Messages.displayClientMessage(player, ModContexts.getTranslatableText("message", "update_checker", "modpack", "line1"));
        Messages.displayClientMessage(player, Texts.translatable(ModContexts.getTranslationKey("message", "update_checker", "modpack", "line2"), coloredLocalModpackVer, coloredOnlineModpackVer));
    }

    public static void checkTranslationUpdateCommand(PlayerEntity player) {
        Text coloredLocalVer = Texts.literal(translation.getVersion()).setStyle(new Style().setColor(Formatting.YELLOW));
        Text coloredOnlineVer = Texts.literal(onlineVersion.translationVersion()).setStyle(new Style().setColor(Formatting.YELLOW));
        Messages.displayClientMessage(player, Texts.translatable(ModContexts.getTranslationKey("message", "update_checker", "translation", "line1"), coloredLocalVer, coloredOnlineVer));
        String updateUrl = translation.getUrl();
        Text message = ModContexts.getTranslatableText("message", "update_checker", "translation", "line2")
                .append(ModContexts.getTranslatableText("message", "update_checker", "translation", "link"))
                .setStyle(new Style()
                        .setClickEvent(GameEvents.clickOpenUrl(updateUrl))
                        .setHoverEvent(GameEvents.hoverShowText(ModContexts.getTranslatableText("message", "update_checker", "translation", "link", "tooltip")))
                        .setColor(Formatting.AQUA)
                )
                .append(ModContexts.getTranslatableText("message", "update_checker", "translation", "line3"));
        Messages.displayClientMessage(player, message);
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();

        if (!RequiredMods.isAllLoaded()) {
            minecraft.openScreen(new MissingModScreen(RequiredMods.getAllMissing(), screen));
        }

        firstTitleScreenShown = true;
    }

    @SuppressWarnings("deprecation")
    private static void showDevelopmentInfo(PlayerEntity player, ModpackInfo.Modpack modpack, ModpackInfo.Translation translation, OnlineVersion onlineVersion) {
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

    private static Text urlText(String label, String url) {
        return Texts.literal(label)
                .append(Texts.literal(url)
                        .setStyle(new Style()
                                .setClickEvent(GameEvents.clickOpenUrl(url))
                                .setColor(Formatting.AQUA)
                        ));
    }
}