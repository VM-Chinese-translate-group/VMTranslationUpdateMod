package top.vmctcn.vmtu.mod.modpack;

import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfo;
import top.vmctcn.vmtu.mod.modpack.info.ModpackInfoReader;
import top.vmctcn.vmtu.mod.modpack.meta.Metadata;
import top.vmctcn.vmtu.mod.modpack.meta.MetadataReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class VersionChecker {
    public static OnlineVersion getOnlineVersion() {
        ModpackInfo.Modpack modpackInfo = ModpackInfoReader.getModpackInfo().getModpack();
        String updateCheckUrl = modpackInfo.getTranslation().getUpdateCheckUrl();
        if (updateCheckUrl == null && modpackInfo.getTranslation().getId() != null) {
            Metadata.Modpacks modpack = MetadataReader.getModpack(modpackInfo.getTranslation().getId());
            String translationVersion = modpack.getTranslationVersion();
            String modpackVersion = modpack.getModpackVersion();

            return new OnlineVersion(translationVersion, modpackVersion);
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
                VMTranslationUpdate.LOGGER.warn("Version check failed: ", e);
                return new OnlineVersion("", "");
            }
        } else {
            VMTranslationUpdate.LOGGER.warn("Version check failed");
            return new OnlineVersion("", "");
        }
    }
}