package top.vmctcn.vmtranslationupdate;

import dev.architectury.event.events.common.PlayerEvent;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

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
                player.sendSystemMessage(new TranslatableText("vmtranslationupdate.message.update", player.getDisplayName().getString(), localVersion, DownloadUtil.getOnlineVersion(player)), Util.NIL_UUID);

                Text message = new TranslatableText("vmtranslationupdate.message.update2")
                        .append(new TranslatableText(downloadUrl).setStyle(
                                Style.EMPTY
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, downloadUrl))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("vmtranslationupdate.message.hover")))
                                        .withColor(Formatting.AQUA)
                        ))
                        .append(new TranslatableText("vmtranslationupdate.message.update3"));

                player.sendSystemMessage(message, Util.NIL_UUID);
            }
        });
    }
}
