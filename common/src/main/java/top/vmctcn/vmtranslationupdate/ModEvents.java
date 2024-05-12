package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import top.vmctcn.vmtranslationupdate.util.*;

import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

public class ModEvents {
    public static void clientTickEndEvent(MinecraftClient client) {
        int tickCounter = VMTranslationUpdate.tickCounter;

        tickCounter++;
        int tickInterval = 20 * 60 * TipsUtil.getTipsMinutes();
        if (tickCounter >= tickInterval) {
            tickCounter = 0;
            CompletableFuture.supplyAsync(() -> TipsUtil.getRandomMessageFromURLAsync(ModConfigUtil.getConfig().tipsUrl))
                    .thenAccept(message -> {
                        if (message == null) return;
                        String randomMessage = TipsUtil.getRandomMessageFromURL(ModConfigUtil.getConfig().tipsUrl);
                        client.player.sendSystemMessage(new TranslatableText(randomMessage), Util.NIL_UUID);
                    });
        }
    }

    public static void playerJoinEvent(ServerPlayerEntity player) {
        NameUtil.playerJoinEvent(player);

        MinecraftClient client = MinecraftClient.getInstance();
        String localVersion = ModConfigUtil.getConfig().modPackTranslationVersion;
        String onlineVersion = VersionCheckUtil.getOnlineVersion(player);

        if (ModConfigUtil.getConfig().checkModPackTranslationUpdate) {
            if (localVersion.equals(onlineVersion)
                    && Files.exists(PackDownloadUtil.resourcePackDir)
                    && !client.options.resourcePacks.contains(PackDownloadUtil.resourcePackName)
                    && !client.options.resourcePacks.contains("file/" + PackDownloadUtil.resourcePackName)) {
                Text message = new TranslatableText("vmtranslationupdate.message.pack", ModConfigUtil.getConfig().translationPackName)
                        .setStyle(Style.EMPTY.withColor(Formatting.GOLD));

                player.sendSystemMessage(message, Util.NIL_UUID);

            } else if (!localVersion.equals(onlineVersion)) {
                Text message = new TranslatableText("vmtranslationupdate.message.update2")
                        .append(new TranslatableText(ModConfigUtil.getConfig().modPackTranslationUrl)
                                .setStyle(Style.EMPTY
                                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ModConfigUtil.getConfig().modPackTranslationUrl))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("vmtranslationupdate.message.hover")))
                                        .withColor(Formatting.AQUA)
                                ))
                        .append(new TranslatableText("vmtranslationupdate.message.update3"));

                player.sendSystemMessage(message, Util.NIL_UUID);
            }
        }
    }
}