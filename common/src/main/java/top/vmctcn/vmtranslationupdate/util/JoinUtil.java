package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JoinUtil {
    public static void playerJoinEvent(ServerPlayerEntity player) {
        String name = player.getName().getString();
        String localVersion = ModConfigUtil.getConfig().modPackTranslationVersion;
        String onlineVersion = VersionCheckUtil.getOnlineVersion(player);

        if (ModConfigUtil.getConfig().playerNameCheck) {
            try {
                String content = HttpUtil.getContentFromURL(ModConfigUtil.getConfig().nameUrl);
                JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();

                if (jsonObject.has(name)) {
                    name = jsonObject.get(name).getAsString();
                    if (ModConfigUtil.getConfig().checkModPackTranslationUpdate && !localVersion.equals(onlineVersion)) {
                        player.sendMessage(Text.translatable("vmtranslationupdate.message.update", name, localVersion, onlineVersion));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
