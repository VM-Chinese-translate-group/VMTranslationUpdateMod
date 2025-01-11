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
import top.vmctcn.vmtranslationupdate.screen.SuggestModScreen;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;

import top.vmctcn.vmtranslationupdate.util.VersionCheckUtil;

public class ModEvents {
    public static boolean firstTitleScreenShown = false;

    public static void playerJoinEvent(ServerPlayerEntity player) {
        String localVersion = ModConfigUtil.getConfig().modPackTranslationVersion;
        String onlineVersion = VersionCheckUtil.getOnlineVersion();

        if (ModConfigUtil.getConfig().checkModPackTranslationUpdate) {
            if (onlineVersion.isEmpty()) {
                player.sendMessage(Text.translatable("vmtranslationupdate.message.error"), false);
                VMTranslationUpdate.LOGGER.warn("Error fetching modpack translation version");
                return;
            }

            if (!localVersion.equals(onlineVersion)) {
                player.sendMessage(Text.translatable("vmtranslationupdate.message.update", localVersion, onlineVersion));
                Text message = Text.translatable("vmtranslationupdate.message.update2")
                        .append(Text.translatable(ModConfigUtil.getConfig().modPackTranslationUrl)
                                .setStyle(Style.EMPTY
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ModConfigUtil.getConfig().modPackTranslationUrl))
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
