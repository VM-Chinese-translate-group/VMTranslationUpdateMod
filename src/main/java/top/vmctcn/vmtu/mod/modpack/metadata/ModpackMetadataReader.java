package top.vmctcn.vmtu.mod.modpack.metadata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModpackMetadataReader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ModpackMetadata metadata;
    private static final Path gamePath = ModPlatform.getGameDir();

    public static ModpackMetadata getMetadata(MetadataType metadataType) {
        Path metadataPath = gamePath.resolve(metadataType.getMetadataFileName());

        if (Files.exists(metadataPath)) {
            try (Reader reader = Files.newBufferedReader(metadataPath, StandardCharsets.UTF_8)) {
                metadata = GSON.fromJson(reader, metadataType.getMetadataClass());
                if (metadata == null) {
                    VMTranslationUpdate.LOGGER.warn("{} is empty or invalid", metadataType.getMetadataFileName());
                    return null;
                }
                return metadata;
            } catch (Exception e) {
                VMTranslationUpdate.LOGGER.warn("Error reading {} {}", metadataType.getMetadataFileName(), e);
            }
        } else {
            VMTranslationUpdate.LOGGER.warn("{} does not exist", metadataType.getMetadataFileName());
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
