package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import top.vmctcn.vmtranslationupdate.config.ModConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class VersionCheckUtil {
    public static String getOnlineVersion(EntityPlayer player) {
        try {
            URL url = new URL(ModConfig.updateUrl);
            URLConnection connection = url.openConnection();

            connection.setConnectTimeout(10000);
            connection.setUseCaches(false);
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.5414.120 Safari/537.36 MCMod/VmTranslationUpdate";
            connection.setRequestProperty("User-Agent", userAgent);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return reader.readLine();
            }
        } catch (Exception e) {
            player.sendMessage(new TextComponentString("VM汉化组：错误！汉化更新检测出现问题。"));
            return "";
        }
    }
}
