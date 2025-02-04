package top.vmctcn.vmtranslationupdate.modpack;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class VersionChecker {
    public static String getOnlineVersion() {
        try {
            URI uri = URI.create(ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getUpdateCheckUrl());
            URLConnection connection = uri.toURL().openConnection();

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0";
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setConnectTimeout(10000);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                return reader.readLine();
            }
        } catch (Exception e) {
            VMTranslationUpdate.LOGGER.warn("Version check failed: ", e);
            return "";
        }
    }
}
