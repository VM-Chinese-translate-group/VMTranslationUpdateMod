package top.vmctcn.vmtranslationupdate;

import dev.architectury.event.events.common.PlayerEvent;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import top.vmctcn.vmtranslationupdate.util.ConfigUtil;
import top.vmctcn.vmtranslationupdate.util.DownloadUtil;

public class VMTranslationUpdateMod {
    public static final String MOD_ID = "vmtranslationupdate";
    
    public static void init() {
        PlayerEvent.PLAYER_JOIN.register((player) -> {
            String localVersion = ConfigUtil.getConfig().translationVersion;
            String onlineVersion = DownloadUtil.getOnlineVersion(player);
            String downloadUrl = DownloadUtil.getDownloadUrl();

            if (onlineVersion != null && !localVersion.equals(onlineVersion)) {
                player.sendMessage(Text.translatable("vmtranslationupdate.message.update", player.getDisplayName().getString(), localVersion, DownloadUtil.getOnlineVersion(player)));

                Text message = Text.translatable("vmtranslationupdate.message.update2")
                        .append(Text.translatable(downloadUrl).setStyle(
                                Style.EMPTY
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, downloadUrl))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("vmtranslationupdate.message.hover")))
                                        .withColor(Formatting.AQUA)
                        ))
                        .append(Text.translatable("vmtranslationupdate.message.update3"));

                player.sendMessage(message);
            }
        });
    }
}
