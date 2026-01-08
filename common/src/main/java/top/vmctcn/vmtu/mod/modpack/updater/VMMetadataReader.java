package top.vmctcn.vmtu.mod.modpack.updater;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class VMMetadataReader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static VMMetadata metadata;
    private static final URI metaUrl = URI.create("https://gitee.com/Wulian233/vmtu/raw/main/update/v2/vm-meta.json");
    public static boolean readMetadataSuccess;

    static {
        try {
            URLConnection connection = metaUrl.toURL().openConnection();
            connection.setRequestProperty("User-Agent", "VMTU-UpdateChecker");
            connection.setConnectTimeout(10000);

            try (Reader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
                metadata = GSON.fromJson(reader, VMMetadata.class);
                readMetadataSuccess = true;
            } catch (Exception e) {
                VMTranslationUpdate.LOGGER.warn("Error reading vm-meta.json.", e);
                readMetadataSuccess = false;
            }
        } catch (IOException e) {
            VMTranslationUpdate.LOGGER.warn("Error getting vm-meta.json.", e);
            readMetadataSuccess = false;
        }
    }

    public static URI getMetaUrl() {
        return metaUrl;
    }

    public static VMMetadata getMetadata() {
        return metadata;
    }

    public static VMMetadata.Modpacks getModpack(String modpackId) {
        if (metadata.getModpacks() == null) {
            VMTranslationUpdate.LOGGER.warn("Error getting modpack info in vm-meta.json.");
            return metadata.getModpacks().get("example");
        }

        return metadata.getModpacks().get(modpackId);
    }
}
