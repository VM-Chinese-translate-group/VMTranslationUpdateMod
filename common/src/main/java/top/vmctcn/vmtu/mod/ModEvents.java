package top.vmctcn.vmtu.mod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.helper.GameEventHelper;
import top.vmctcn.vmtu.mod.modpack.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.VersionChecker;
import top.vmctcn.vmtu.mod.screen.SuggestModScreen;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(PlayerEntity player) {
        if (player == null) return;

        ModpackInfo.Modpack modpack = ModpackInfoReader.getModpackInfo().getModpack();
        ModpackInfo.Translation translation = modpack.getTranslation();

        String localTranslationVersion = translation.getVersion();
        String localModpackVersion = modpack.getVersion();

        VersionChecker.OnlineVersionInfo onlineInfo = VersionChecker.getOnlineVersionInfo();

        if (ModConfigHelper.getConfig().testMode) {
            player.sendMessage(Text.literal("==================== VMTU testMode ===================="), false);
            player.sendMessage(Text.literal("Modpack Name: " + modpack.getName()), false);
            player.sendMessage(Text.literal("Modpack Version: " + modpack.getVersion()), false);
            player.sendMessage(Text.literal("Modpack Translation URL:§b " + translation.getUrl()), false);
            player.sendMessage(Text.literal("Modpack Translation Update Check URL:§b " + translation.getUpdateCheckUrl()), false);
            player.sendMessage(Text.literal("Modpack Translation Language: " + translation.getLanguage()), false);
            player.sendMessage(Text.literal("Modpack Translation Version: " + translation.getVersion()), false);
            player.sendMessage(Text.literal("Modpack Translation Resource Pack Name: " + translation.getResourcePackName()), false);
            player.sendMessage(Text.literal("Online Translation Version: " + onlineInfo.translationVersion()), false);
            player.sendMessage(Text.literal("Online Modpack Version: " + onlineInfo.modpackVersion()), false);
        }

        if (ModConfigHelper.getConfig().checkModPackTranslationUpdate) {
            if (!onlineInfo.isValid()) {
                player.sendMessage(Text.translatable("vmtranslationupdate.message.error"), false);
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            boolean translationUpdateNeeded = !localTranslationVersion.equals(onlineInfo.translationVersion());
            boolean modpackUpdateNeeded = !onlineInfo.modpackVersion().isEmpty() && !localModpackVersion.equals(onlineInfo.modpackVersion());
            if (translationUpdateNeeded) {
                player.sendMessage(Text.translatable("vmtranslationupdate.message.update", localTranslationVersion, onlineInfo.translationVersion()), false);
                String updateUrl = translation.getUrl();
                Text message = Text.translatable("vmtranslationupdate.message.update2")
                        .append(Text.translatable(updateUrl)
                                .setStyle(Style.EMPTY
                                        .withClickEvent(GameEventHelper.clickOpenUrl(updateUrl))
                                        .withHoverEvent(GameEventHelper.hoverShowText(Text.translatable("vmtranslationupdate.message.hover")))
                                        .withColor(Formatting.AQUA)
                                ))
                        .append(Text.translatable("vmtranslationupdate.message.update3"));
                player.sendMessage(message, false);

                if (modpackUpdateNeeded){
                    player.sendMessage(Text.translatable("vmtranslationupdate.message.update_modpack"), false);
                    player.sendMessage(Text.translatable("vmtranslationupdate.message.update_modpack_hint", localModpackVersion, onlineInfo.modpackVersion()), false);
                }
            }
        }
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        boolean needI18n = ModConfigHelper.getConfig().i18nUpdateModCheck && !SuggestModScreen.i18nUpdateModPresent;
        boolean needVP = ModConfigHelper.getConfig().vaultPatcherCheck && !SuggestModScreen.vaultPatcherPresent;

        // 只要有任何一个模组需要提示，就显示屏幕
        if (needI18n || needVP) {
            MinecraftClient.getInstance().setScreen(new SuggestModScreen(screen));
        }

        firstTitleScreenShown = true;
    }
}
