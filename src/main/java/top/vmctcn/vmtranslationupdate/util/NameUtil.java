package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.entity.player.EntityPlayer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.text.TextComponentTranslation;
import top.vmctcn.vmtranslationupdate.config.ModConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class NameUtil {
    public static void getPlayerName(EntityPlayer player) {
        String name = player.getName();
        String localVersion = ModConfig.modPackTranslationVersion;
        String onlineVersion = VersionCheckUtil.getOnlineVersion(player);

        if (ModConfig.playerNameCheck) {
            if (name.equals("Zi__Min")) {
                name = "岷叔";
                player.sendMessage(new TextComponentTranslation("vmtranslationupdate.message.zimin"));
                if (ModConfig.checkModPackTranslationUpdate && !localVersion.equals(onlineVersion)) {
                    player.sendMessage(new TextComponentTranslation("vmtranslationupdate.message.update", name, localVersion, VersionCheckUtil.getOnlineVersion(player)));
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
                            player.sendMessage(new TextComponentTranslation("vmtranslationupdate.message.update", name, localVersion, VersionCheckUtil.getOnlineVersion(player)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
