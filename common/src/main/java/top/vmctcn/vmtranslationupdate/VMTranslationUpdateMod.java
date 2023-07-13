package top.vmctcn.vmtranslationupdate;

import dev.architectury.event.events.common.PlayerEvent;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.*;
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
                player.sendMessage(new TranslatableComponent("vmtranslationupdate.message.update", player.getDisplayName().getString(), localVersion, DownloadUtil.getOnlineVersion(player)), Util.NIL_UUID);

                Component message = new TranslatableComponent("vmtranslationupdate.message.update2")
                        .append(new TranslatableComponent(downloadUrl).withStyle(
                                Style.EMPTY
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, downloadUrl))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("vmtranslationupdate.message.hover")))
                                        .withColor(ChatFormatting.AQUA)
                        ))
                        .append(new TranslatableComponent("vmtranslationupdate.message.update3"));

                player.sendMessage(message, Util.NIL_UUID);
            }
        });
    }
}
