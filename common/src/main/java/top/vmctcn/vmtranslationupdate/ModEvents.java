package top.vmctcn.vmtranslationupdate;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import top.vmctcn.vmtranslationupdate.util.*;

import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModEvents {
    private static boolean isLoadedTips = false; // 标识Tips是否已经从网络加载
    public static void clientTickEndEvent(MinecraftClient client) {
        int tickCounter = VMTranslationUpdate.tickCounter;

        tickCounter++;
        int tickInterval = 20 * 60 * TipsUtil.getTipsMinutes();
        if (tickCounter >= tickInterval) {
            tickCounter = 0;
            if (!isLoadedTips) {
                CompletableFuture.supplyAsync(() -> TipsUtil.getRandomMessageFromURLAsync(ModConfigUtil.getConfig().tipsUrl))
                        .thenAccept(message -> {
                            isLoaded = true;
                            if (message == null) return;
                            String randomMessage = getRandomMessageFromCache();
                            if (randomMessage != null) {
                                Objects.requireNonNull(client.player).sendMessage(Text.translatable(randomMessage));
                            }
                        });
            } else {
                // 如果已经加载过提示信息，直接从缓存中获取随机消息
                String randomMessage = getRandomMessageFromCache();
                if (randomMessage != null) {
                    Objects.requireNonNull(client.player).sendMessage(Text.translatable(randomMessage));
                }
            }
        }
    }

    private static String getRandomMessageFromCache() {
        if (!TipsUtil.messagesList.isEmpty()) {
            int index = VMTranslationUpdate.random.nextInt(TipsUtil.messagesList.size());
            return TipsUtil.messagesList.get(index);
        }
        return null;
    }

    public static void playerJoinEvent(ServerPlayerEntity player) {
        JoinUtil.playerJoinEvent(player);

        MinecraftClient client = MinecraftClient.getInstance();
        String localVersion = ModConfigUtil.getConfig().modPackTranslationVersion;
        String onlineVersion = VersionCheckUtil.getOnlineVersion(player);

        if (ModConfigUtil.getConfig().checkModPackTranslationUpdate) {
            if (!localVersion.equals(onlineVersion)) {
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
}
