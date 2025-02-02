package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtranslationupdate.config.ModConfigHelper;
import top.vmctcn.vmtranslationupdate.modpack.ModpackInfoReader;
import top.vmctcn.vmtranslationupdate.modpack.VersionChecker;
import top.vmctcn.vmtranslationupdate.screen.SuggestModScreen;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(ServerPlayerEntity player) {
        String localVersion = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getVersion();
        String onlineVersion = VersionChecker.getOnlineVersion();

        if (ModConfigHelper.getConfig().checkModPackTranslationUpdate) {
            if (onlineVersion.isEmpty()) {
                player.sendMessage(new TranslatableText("vmtranslationupdate.message.error"), false);
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            if (!localVersion.equals(onlineVersion)) {
                String updateUrl = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getUrl();
                player.sendMessage(new TranslatableText("vmtranslationupdate.message.update", localVersion, onlineVersion),false);

                Text message = new TranslatableText("vmtranslationupdate.message.update2")
                        .append(new TranslatableText(updateUrl)
                                .setStyle(Style.EMPTY
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, updateUrl))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("vmtranslationupdate.message.hover")))
                                        .withColor(Formatting.AQUA)
                                ))
                        .append(new TranslatableText("vmtranslationupdate.message.update3"));
                player.sendMessage(message, false);
            }
        }
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        String language = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();

        if ("zh_cn".equals(language)) {
            MinecraftClient.getInstance().setScreen(new SuggestModScreen(screen));
        }

        firstTitleScreenShown = true;
    }
}
