package top.vmctcn.vmtranslationupdate;

import dev.architectury.event.events.common.PlayerEvent;

import net.minecraft.Util;
import net.minecraft.network.chat.*;
import top.vmctcn.vmtranslationupdate.util.ConfigUtil;
import top.vmctcn.vmtranslationupdate.util.DownloadUtil;

public class VMTranslationUpdateMod {
    public static final String MOD_ID = "vmupdate";
    
    public static void init() {
        PlayerEvent.PLAYER_JOIN.register((player) -> {
            String localVersion = ConfigUtil.getConfig().translationVersion;
            String onlineVersion = DownloadUtil.getOnlineVersion(player);
            String Downloadurl = DownloadUtil.getDownloadUrl();

            if (onlineVersion != null && !localVersion.equals(onlineVersion)) {
                player.sendMessage(new TranslatableComponent("vmupdate.message.update", player.getDisplayName().getString(), localVersion, DownloadUtil.getOnlineVersion(player)), Util.NIL_UUID);

                Component message = new TranslatableComponent("vmupdate.message.update2")
                        .append(new TranslatableComponent(Downloadurl).withStyle(
                                Style.EMPTY
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Downloadurl))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("vmupdate.message.hover")))
                                        .withColor(net.minecraft.ChatFormatting.AQUA)
                        ))
                        .append(new TranslatableComponent("vmupdate.message.update3"));

                player.sendMessage(message, Util.NIL_UUID);
            }
        });
    }
}
