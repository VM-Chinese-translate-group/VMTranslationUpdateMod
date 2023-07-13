package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class DownloadUtil {
    public static String getOnlineVersion(PlayerEntity player) {
        try {
            String urlStr = getUpdateUrl();
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();

            connection.setConnectTimeout(10000);
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5414.120 Safari/537.36 MCMod/VmUpdate";
            connection.setRequestProperty("User-Agent", userAgent);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return reader.readLine();
            }
        } catch (Exception e) {
            player.sendMessage(Text.translatable("vmupdate.message.error"));
            return "";
        }
    }

    public static String getDownloadUrl() {
        return ConfigUtil.getConfig().downloadUrl;
    }

    public static String getUpdateUrl() {
        return ConfigUtil.getConfig().updateUrl;
    }
}
