package top.vmctcn.vmtu.mod.modpack.metadata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModpackMetadataReader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ModpackMetadata metadata;

    public static ModpackMetadata getMetadata(MetadataType metadataType) {
        Path gamePath = ModPlatform.getGameDir();
        if (gamePath == null) {
            ModContexts.LOGGER.error("Game directory is null, cannot read metadata");
            return null;
        }
        Path metadataPath = gamePath.resolve(metadataType.getMetadataFileName());

        if (Files.exists(metadataPath)) {
            try (Reader reader = Files.newBufferedReader(metadataPath, StandardCharsets.UTF_8)) {
                metadata = GSON.fromJson(reader, metadataType.getMetadataClass());
                if (metadata == null) {
                    ModContexts.LOGGER.warn("{} is empty or invalid", metadataType.getMetadataFileName());
                    return null;
                }
                return metadata;
            } catch (Exception e) {
                ModContexts.LOGGER.warn("Error reading {} {}", metadataType.getMetadataFileName(), e);
            }
        } else {
            ModContexts.LOGGER.warn("{} does not exist", metadataType.getMetadataFileName());
        }

        return null;
    }

    public static ModpackMetadata getMetadata() {
        for (MetadataType metadataType : MetadataType.values()) {
            ModpackMetadata metadata = getMetadata(metadataType);
            if (metadata != null) {
                return metadata;
            }
        }
        return null;
    }
}
