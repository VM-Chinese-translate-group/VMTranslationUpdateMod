package top.vmctcn.vmtranslationupdate.util;

import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.util.Util;
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
            String onlineVersion = VersionCheckUtil.getOnlineVersion(player);

            if (name.equals("Zi__Min")) {
                name = "岷叔";
                player.sendMessage(Text.translatable("vmtranslationupdate.message.zimin"));
                if (!localVersion.equals(onlineVersion)) {
                    player.sendMessage(Text.translatable("vmtranslationupdate.message.update", name, localVersion, VersionCheckUtil.getOnlineVersion(player)));
                }
            }else {
                try {
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

                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(stringBuilder.toString()).getAsJsonObject();

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
        });
    }
}