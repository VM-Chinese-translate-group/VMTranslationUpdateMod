package top.vmctcn.vmtranslationupdate.event;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfig;
import top.vmctcn.vmtranslationupdate.util.NameUtil;
import top.vmctcn.vmtranslationupdate.util.TipsUtil;
import top.vmctcn.vmtranslationupdate.util.VersionCheckUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class ModEventHandler {
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.player;
        String localVersion = ModConfig.modPackTranslationVersion;
        String onlineVersion = VersionCheckUtil.getOnlineVersion(player);
        String name = player.getDisplayNameString();

        NameUtil.getPlayerName(player);

        if (ModConfig.playerNameCheck) {
            if (name.equals("Zi__Min")) {
                name = "岷叔";
                player.sendMessage(new TranslatableText("vmtranslationupdate.message.zimin"));
                if (ModConfig.checkModPackTranslationUpdate && !localVersion.equals(onlineVersion)) {
                    player.sendMessage(new TranslatableText("vmtranslationupdate.message.update", name, localVersion, VersionCheckUtil.getOnlineVersion(player)));
                }
            } else {
                try {
                    URL url = new URL(ModConfig.nameUrl);
                    URLConnection connection = url.openConnection();
                    connection.setConnectTimeout(10000);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();

                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(stringBuilder.toString()).getAsJsonObject();

                    if (jsonObject.has(name)) {
                        name = jsonObject.get(name).getAsString();
                        if (ModConfig.checkModPackTranslationUpdate && !localVersion.equals(onlineVersion)) {
                            player.sendMessage(new TranslatableText("vmtranslationupdate.message.update", name, localVersion, VersionCheckUtil.getOnlineVersion(player)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && MinecraftClient.getInstance().world != null) {
            VMTranslationUpdate.tickCounter++;
            int tickInterval = 20 * 60 * TipsUtil.getTipsMinutes();
            if (VMTranslationUpdate.tickCounter >= tickInterval) {
                VMTranslationUpdate.tickCounter = 0;
                CompletableFuture.supplyAsync(() -> TipsUtil.getRandomMessageFromURLAsync(ModConfig.tipsUrl))
                        .thenAccept(message -> {
                            String randomMessage = TipsUtil.getRandomMessageFromURL(ModConfig.tipsUrl);
                            if (message != null) {
                                if (VMTranslationUpdate.client.player != null) {
                                    VMTranslationUpdate.client.player.sendMessage(new LiteralText(randomMessage));
                                }
                            }
                        });
            }
        }
    }
}