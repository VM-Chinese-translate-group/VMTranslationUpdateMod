package top.vmctcn.vmtu.mod.modpack.metadata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtu.libraries.resourcepack.util.AssetUtil;
import top.vmctcn.vmtu.mod.ModContexts;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class VMMetadataReader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static VMMetadata metadata;
    private static final URI metaUrl = URI.create(AssetUtil.getFastestUrl() + "update/v2/vm-meta.json");
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
                ModContexts.LOGGER.warn("Error reading vm-meta.json.", e);
                readMetadataSuccess = false;
            }
        } catch (IOException e) {
            ModContexts.LOGGER.warn("Error getting vm-meta.json.", e);
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
        if (metadata == null) {
            ModContexts.LOGGER.warn("Error getting modpack info in vm-meta.json: metadata is null.");
            return null;
        }

        if (metadata.getModpacks() == null) {
            ModContexts.LOGGER.warn("Error getting modpack info in vm-meta.json: modpacks is null.");
            return null;
        }

        VMMetadata.Modpacks modpack = metadata.getModpacks().get(modpackId);
        if (modpack == null) {
            ModContexts.LOGGER.warn("Error getting modpack info in vm-meta.json: missing modpack id '{}'.", modpackId);
        }

        return modpack;
    }
}
