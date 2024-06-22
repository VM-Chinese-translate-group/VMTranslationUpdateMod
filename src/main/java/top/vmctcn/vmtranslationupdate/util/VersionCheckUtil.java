package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import top.vmctcn.vmtranslationupdate.config.ModConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class VersionCheckUtil {
    public static String getOnlineVersion(PlayerEntity player) {
        try {
            URL url = new URL(ModConfig.modPackTranslationUpdateCheckUrl);
            URLConnection connection = url.openConnection();

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5414.120 Safari/537.36 MCMod/VmUpdate";
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setConnectTimeout(10000);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return reader.readLine();
            }
        } catch (Exception e) {
            player.sendMessage(new TranslatableText("vmtranslationupdate.message.error"));
            return "";
        }
    }
}
