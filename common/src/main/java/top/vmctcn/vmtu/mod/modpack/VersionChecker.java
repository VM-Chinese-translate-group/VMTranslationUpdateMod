package top.vmctcn.vmtu.mod.modpack;

import top.vmctcn.vmtu.mod.VMTranslationUpdate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class VersionChecker {
    public record OnlineVersionInfo(String translationVersion, String modpackVersion) {
        public boolean isValid() {
            return translationVersion != null && !translationVersion.isEmpty();
        }
    }

    public static OnlineVersionInfo getOnlineVersionInfo() {
        try {
            URI uri = new URI(ModpackInfoReader.getModpackInfo().getModpack().getTranslation().getUpdateCheckUrl());
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();

            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36 Edg/138.0.0.0 MCMod/VmUpdate";
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setConnectTimeout(10000);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String translationVersion = reader.readLine();
                String modpackVersion = reader.readLine();  // optional second line
                return new OnlineVersionInfo(translationVersion == null ? "" : translationVersion.trim(),
                        modpackVersion == null ? "" : modpackVersion.trim());
            }
        } catch (Exception e) {
            VMTranslationUpdate.LOGGER.warn("Version check failed: ", e);
            return new OnlineVersionInfo("", "");
        }
    }
}
