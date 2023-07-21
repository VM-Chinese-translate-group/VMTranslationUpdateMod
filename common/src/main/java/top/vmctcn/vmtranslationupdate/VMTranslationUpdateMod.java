package top.vmctcn.vmtranslationupdate;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.PlayerEvent;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import top.vmctcn.vmtranslationupdate.util.ConfigUtil;
import top.vmctcn.vmtranslationupdate.util.DownloadUtil;
import top.vmctcn.vmtranslationupdate.util.TipsUtil;

import java.util.Random;

import static top.vmctcn.vmtranslationupdate.util.TipsUtil.sendRandomMessage;

public class VMTranslationUpdateMod {
    public static Random random;
    public static int tickCounter;
    public static Integer minutes = TipsUtil.getMinutes();
    public static final String MOD_ID = "vmtranslationupdate";
    
    public static Integer init() {
        random = new Random();

        ClientTickEvent.CLIENT_POST.register(level -> {
            tickCounter++;
            int tickInterval = 20 * 60 * minutes;

            if (tickCounter >= tickInterval) {
                tickCounter = 0;
                sendRandomMessage();
            }
        });

        PlayerEvent.PLAYER_JOIN.register((player) -> {
            String localVersion = ConfigUtil.getConfig().translationVersion;
            String onlineVersion = DownloadUtil.getOnlineVersion(player);
            String downloadUrl = DownloadUtil.getDownloadUrl();
            String name = player.getDisplayName().getString();
            if (name.equals("Zi__Min")) {
                name = "岷叔";
                player.sendSystemMessage(new TranslatableText("欢迎来到籽岷的Minecraft游戏世界！"),Util.NIL_UUID);
            }

            if (onlineVersion != null && !localVersion.equals(onlineVersion)) {
                player.sendSystemMessage(new TranslatableText("vmtranslationupdate.message.update", name, localVersion, DownloadUtil.getOnlineVersion(player)), Util.NIL_UUID);

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

        return null;
    }


}
