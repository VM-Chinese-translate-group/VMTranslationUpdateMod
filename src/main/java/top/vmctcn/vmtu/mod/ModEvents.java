package top.vmctcn.vmtu.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.text.*;
import top.vmctcn.vmtu.mod.modpack.OnlineVersion;
import top.vmctcn.vmtu.mod.modpack.VersionChecker;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.helper.GameEventHelper;
import top.vmctcn.vmtu.mod.helper.LanguageHelper;
import top.vmctcn.vmtu.mod.screen.SuggestModScreen;

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

        if (ModConfigs.testMode) {
            player.sendMessage(new LiteralText("==================== VMTU testMode ===================="));
            player.sendMessage(new LiteralText("Modpack Name: " + modpack.getName()));
            player.sendMessage(new LiteralText("Modpack Version: " + modpack.getVersion()));
            player.sendMessage(new LiteralText("Modpack Translation URL:§b " + translation.getUrl()));
            if (translation.getUpdateCheckUrl() != null) {
                player.sendMessage(new LiteralText("Modpack Translation Update Check URL:§b " + translation.getUpdateCheckUrl()));
            } else {
                player.sendMessage(new LiteralText("Modpack Translation Update Check URL:§b Used vm-meta v2, deprecated this"));
            }
            player.sendMessage(new LiteralText("Modpack Translation Language: " + translation.getLanguage()));
            player.sendMessage(new LiteralText("Modpack Translation Version: " + translation.getVersion()));
            player.sendMessage(new LiteralText("Modpack Translation Resource Pack Name: " + translation.getResourcePackName()));
            player.sendMessage(new LiteralText("Online Translation Version: " + onlineVersion.translationVersion));
            player.sendMessage(new LiteralText("Online Modpack Version: " + onlineVersion.modpackVersion));
            player.sendMessage(new LiteralText("======================================================="));
        }

        if (!translation.getLanguage().equals(languageManager.getLanguage().getCode()) && LanguageHelper.isChineseLanguage()) {
            player.sendMessage(new TranslatableText("vmtranslationupdate.message.not_support", translation.getLanguage()));
        }

        if (ModConfigs.checkModPackTranslationUpdate) {
            if (!onlineVersion.isValid()) {
                player.sendMessage(new TranslatableText("vmtranslationupdate.message.error"));
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            boolean translationUpdateNeeded = !localTranslationVersion.equals(onlineVersion.translationVersion);
            boolean modpackUpdateNeeded = !onlineVersion.modpackVersion.isEmpty() && !localModpackVersion.equals(onlineVersion.modpackVersion);
            Text coloredLocalVer = new LiteralText(localTranslationVersion).setStyle(new Style().setColor(Formatting.YELLOW));
            Text coloredOnlineVer = new LiteralText(onlineVersion.translationVersion).setStyle(new Style().setColor(Formatting.YELLOW));

            if (translationUpdateNeeded) {
                player.sendMessage(new TranslatableText("vmtranslationupdate.message.update", coloredLocalVer, coloredOnlineVer));
                String updateUrl = translation.getUrl();
                Text message = new TranslatableText("vmtranslationupdate.message.update2")
                        .append(new TranslatableText(updateUrl)
                                .setStyle(new Style()
                                        .setClickEvent(GameEventHelper.clickOpenUrl(updateUrl))
                                        .setHoverEvent(GameEventHelper.hoverShowText(new TranslatableText("vmtranslationupdate.message.hover")))
                                        .setColor(Formatting.AQUA)
                                ))
                        .append(new TranslatableText("vmtranslationupdate.message.update3"));
                player.sendMessage(message);

                if (modpackUpdateNeeded){
                    Text coloredLocalModpackVer = new LiteralText(localModpackVersion).setStyle(new Style().setColor(Formatting.YELLOW));
                    Text coloredOnlineModpackVer = new LiteralText(onlineVersion.modpackVersion).setStyle(new Style().setColor(Formatting.YELLOW));
                    player.sendMessage(new TranslatableText("vmtranslationupdate.message.update_modpack"));
                    player.sendMessage(new TranslatableText("vmtranslationupdate.message.update_modpack_hint", coloredLocalModpackVer, coloredOnlineModpackVer));
                }
            }
        }
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (LanguageHelper.isChineseLanguage()) {
            if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
                return;
            }

            boolean needI18n = ModConfigs.i18nUpdateModCheck && !SuggestModScreen.i18nUpdateModPresent;
            boolean needVP = ModConfigs.vaultPatcherCheck && !SuggestModScreen.vaultPatcherPresent;

            // 只要有任何一个模组需要提示，就显示屏幕
            if (needI18n || needVP) {
                Minecraft.getInstance().openScreen(new SuggestModScreen(screen));
            }

            firstTitleScreenShown = true;
        }
    }
}