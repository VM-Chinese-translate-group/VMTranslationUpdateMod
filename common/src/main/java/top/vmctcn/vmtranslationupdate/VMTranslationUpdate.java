package top.vmctcn.vmtranslationupdate;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.PlayerEvent;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import top.vmctcn.vmtranslationupdate.util.*;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import static top.vmctcn.vmtranslationupdate.util.PackUtil.*;

public class VMTranslationUpdate {
    public static Random random;
    public static int tickCounter;
    public static final String MOD_ID = "vmtranslationupdate";
    static MinecraftClient mc = MinecraftClient.getInstance();

    public static void init() {
        random = new Random();
        PackUtil.Download();
        ClientTickEvent.CLIENT_POST.register(level -> {
            tickCounter++;
            int tickInterval = 20 * 60 * TipsUtil.getMinutes();
            if (tickCounter >= tickInterval) {
                tickCounter = 0;
                CompletableFuture.supplyAsync(() -> TipsUtil.getRandomMessageFromURLAsync(ConfigUtil.getConfig().tipsUrl))
                        .thenAccept(message -> {
                            String randomMessage = TipsUtil.getRandomMessageFromURL(ConfigUtil.getConfig().tipsUrl);
                            if (message != null) {
                                if (mc.player != null) {
                                    mc.player.sendSystemMessage(new TranslatableText(randomMessage), Util.NIL_UUID);
                                }
                            }
                        });
            }
        });

        PlayerEvent.PLAYER_JOIN.register((player) -> {
            String localVersion = ConfigUtil.getConfig().translationVersion;
            String onlineVersion = DownloadUtil.getOnlineVersion(player).substring(0,5);
            String name = player.getName().getString();

            if (name.equals("Zi__Min")) {
                name = "岷叔";
                player.sendSystemMessage(new TranslatableText("vmtranslationupdate.message.zimin") ,Util.NIL_UUID);
            }

            if (!localVersion.equals(onlineVersion)) {
                player.sendSystemMessage(new TranslatableText("vmtranslationupdate.message.update", name, localVersion, DownloadUtil.getOnlineVersion(player)), Util.NIL_UUID);
                Text message = new TranslatableText("vmtranslationupdate.message.update2")
                        .append(new TranslatableText(ConfigUtil.getConfig().downloadUrl).setStyle(
                                Style.EMPTY
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,ConfigUtil.getConfig().downloadUrl))
                                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("vmtranslationupdate.message.hover")))
                                    .withColor(Formatting.AQUA)
                        ))
                        .append(new TranslatableText("vmtranslationupdate.message.update3"));

                player.sendSystemMessage(message, Util.NIL_UUID);
            }
            LocalDate currentDate = LocalDate.now();
            if (new File(saveDirectory.toFile(), packName).exists() &&!mc.options.resourcePacks.contains(packName) && !mc.options.resourcePacks.contains("file/" + packName) 
            && (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                Text message = new TranslatableText("vmtranslationupdate.message.pack", ConfigUtil.getConfig().packName).setStyle(Style.EMPTY.withColor(Formatting.GOLD));
                player.sendSystemMessage(message, Util.NIL_UUID);
            }
        });
    }
}