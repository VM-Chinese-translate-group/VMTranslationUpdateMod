package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtranslationupdate.modpack.ModpackInfoReader;
import top.vmctcn.vmtranslationupdate.screen.SuggestModScreen;
import top.vmctcn.vmtranslationupdate.config.ModConfigHelper;

import top.vmctcn.vmtranslationupdate.modpack.VersionChecker;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(ServerPlayerEntity player) {
        String localVersion = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getVersion();
        String onlineVersion = VersionChecker.getOnlineVersion();

        if (ModConfigHelper.getConfig().checkModPackTranslationUpdate) {
            if (onlineVersion.isEmpty()) {
                player.sendMessage(Text.translatable("vmtranslationupdate.message.error"), false);
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            if (!localVersion.equals(onlineVersion)) {
                String updateUrl = ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getUrl();
                player.sendMessage(Text.translatable("vmtranslationupdate.message.update", localVersion, onlineVersion));
                Text message = Text.translatable("vmtranslationupdate.message.update2")
                        .append(Text.translatable(updateUrl)
                                .setStyle(Style.EMPTY
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, updateUrl))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("vmtranslationupdate.message.hover")))
                                        .withColor(Formatting.AQUA)
                                ))
                        .append(Text.translatable("vmtranslationupdate.message.update3"));
                player.sendMessage(message);
            }
        }
    }

    public static void screenAfterInitEvent(Screen screen) {
        if (firstTitleScreenShown || !(screen instanceof TitleScreen)) {
            return;
        }

        String language = MinecraftClient.getInstance().getLanguageManager().getLanguage();

        if ("zh_cn".equals(language)) {
            MinecraftClient.getInstance().setScreen(new SuggestModScreen(screen));
        }

        firstTitleScreenShown = true;
    }
}
