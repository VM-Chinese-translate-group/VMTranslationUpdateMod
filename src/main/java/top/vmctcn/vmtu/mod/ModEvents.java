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
import top.vmctcn.vmtu.mod.screen.MissingModScreen;
import top.vmctcn.vmtu.mod.utils.RequiredMods;
import top.vmctcn.vmtu.multiversion.GameEvents;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;
import top.vmctcn.vmtu.multiversion.Messages;
import top.vmctcn.vmtu.multiversion.Texts;

public class ModEvents {
    private static boolean firstTitleScreenShown = false;

    public static ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
    public static ModpackInfo.Translation translation = modpack.getTranslation();
    public static OnlineVersion onlineVersion = VersionChecker.getOnlineVersion(modpack);

    public static void playerJoinEvent(PlayerEntity player) {
        if (player == null) return;

        LanguageManager languageManager = Minecraft.getInstance().getLanguageManager();
        String language = languageManager.getLanguage().getCode();

        String localTranslationVersion = translation.getVersion();
        String localModpackVersion = modpack.getVersion();

        if (ModConfigs.misc.devMode) {
            Messages.displayClientMessage(player, Texts.literal("==================== VMTU Dev Mode ===================="));
            Messages.displayClientMessage(player, Texts.literal("Modpack Name: " + modpack.getName()));
            Messages.displayClientMessage(player, Texts.literal("Modpack Version: " + modpack.getVersion()));
            Text translationUrlMessage = Texts.literal("Modpack Translation URL:")
                    .append(Texts.literal(translation.getUrl())
                            .setStyle(new Style()
                                    .setClickEvent(GameEvents.clickOpenUrl(translation.getUrl()))
                                    .setColor(Formatting.AQUA)
                            )
                    );
            Messages.displayClientMessage(player, translationUrlMessage);
            if (translation.getUpdateCheckUrl() != null) {
                Text updateCheckUrlMessage = Texts.literal("Modpack Translation Update Check URL:")
                        .append(Texts.literal(translation.getUpdateCheckUrl())
                                .setStyle(new Style()
                                        .setClickEvent(GameEvents.clickOpenUrl(translation.getUpdateCheckUrl()))
                                        .setColor(Formatting.AQUA)
                                )
                        );
                Messages.displayClientMessage(player, updateCheckUrlMessage);
            }
            Messages.displayClientMessage(player, Texts.literal("Modpack Translation Language: " + translation.getLanguage()));
            Messages.displayClientMessage(player, Texts.literal("Modpack Translation Version: " + translation.getVersion()));
            if (translation.getResourcePackName() != null) {
                Messages.displayClientMessage(player, Texts.literal("Modpack Translation Resource Pack Name: " + translation.getResourcePackName()));
            }
            Messages.displayClientMessage(player, Texts.literal("Online Translation Version: " + onlineVersion.translationVersion()));
            Messages.displayClientMessage(player, Texts.literal("Online Modpack Version: " + onlineVersion.modpackVersion()));
            Messages.displayClientMessage(player, Texts.literal("======================================================="));
        }

        if (!translation.getLanguage().equals(language) && LanguageUtils.isChineseLanguage()) {
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

            if (translationUpdateNeeded) {
                checkTranslationUpdateCommand(player);
                if (modpackUpdateNeeded){
                    checkModpackUpdateCommand(player);
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
}