package top.vmctcn.vmtranslationupdate.util;

import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.text.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class NameUtil {
    public static void init() {
        PlayerEvent.PLAYER_JOIN.register((player) -> {
            String name = player.getName().getString();
            String localVersion = ModConfigUtil.getConfig().translationVersion;

            if (name.equals("Zi__Min")) {
                String onlineVersion = VersionCheckUtil.getOnlineVersion(player);
                name = "岷叔";
                player.sendMessage(Text.translatable("vmtranslationupdate.message.zimin"));
                if (!localVersion.equals(onlineVersion) && !ModConfigUtil.getConfig().updateUrl.isEmpty()) {
                    player.sendMessage(Text.translatable("vmtranslationupdate.message.update", name, localVersion, VersionCheckUtil.getOnlineVersion(player)));
                }
            } else {
                if (!ModConfigUtil.getConfig().updateUrl.isEmpty()) {
                    try {
                        String onlineVersion = VersionCheckUtil.getOnlineVersion(player);
                        URL url = new URL(ModConfigUtil.getConfig().nameUrl);
                        URLConnection connection = url.openConnection();
                        connection.setConnectTimeout(10000);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        reader.close();

                        JsonObject jsonObject = JsonParser.parseString(stringBuilder.toString()).getAsJsonObject();

                        if (jsonObject.has(name)) {
                            name = jsonObject.get(name).getAsString();
                            if (!localVersion.equals(onlineVersion)) {
                                player.sendMessage(Text.translatable("vmtranslationupdate.message.update", name, localVersion, VersionCheckUtil.getOnlineVersion(player)));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}