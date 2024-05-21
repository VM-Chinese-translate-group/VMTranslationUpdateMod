package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class VersionCheckUtil {
    public static String getOnlineVersion(PlayerEntity player) {
        try {
            URI uri = URI.create(ModConfigUtil.getConfig().modPackTranslationUpdateCheckUrl);
            URLConnection connection = uri.toURL().openConnection();

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5414.120 Safari/537.36 MCMod/VmUpdate";
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setConnectTimeout(10000);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return reader.readLine();
            }
        } catch (Exception e) {
            player.sendMessage(Text.translatable("vmtranslationupdate.message.error"));
            return "";
        }
    }
}
