package top.vmctcn.vmtu.mod.modpack.updater;

import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class VersionChecker {
    public static OnlineVersion getOnlineVersion(ModpackInfo.Modpack modpackInfo) {
        String updateCheckUrl = modpackInfo.getTranslation().getUpdateCheckUrl();
        if ((updateCheckUrl == null || updateCheckUrl.isEmpty()) && modpackInfo.getTranslation().getId() != null && VMMetadataReader.readMetadataSuccess) {
            VMMetadata.Modpacks modpacks = VMMetadataReader.getModpack(modpackInfo.getTranslation().getId());
            return new OnlineVersion(modpacks.getTranslationVersion(), modpacks.getModpackVersion());
        } else if (updateCheckUrl != null) {
            try {
                URI uri = URI.create(updateCheckUrl);
                URLConnection connection = uri.toURL().openConnection();
                connection.setRequestProperty("User-Agent", "VMTU-UpdateChecker");
                connection.setConnectTimeout(10000);

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String translationVersion = reader.readLine();
                    String modpackVersion = reader.readLine();  // optional second line
                    return new OnlineVersion(translationVersion == null ? "" : translationVersion.trim(),
                            modpackVersion == null ? "" : modpackVersion.trim());
                }
            } catch (Exception e) {
                ModContexts.LOGGER.warn("Version check failed: ", e);
                return new OnlineVersion("", "");
            }
        } else {
            ModContexts.LOGGER.warn("Version check failed");
            return new OnlineVersion("", "");
        }
    }
}
