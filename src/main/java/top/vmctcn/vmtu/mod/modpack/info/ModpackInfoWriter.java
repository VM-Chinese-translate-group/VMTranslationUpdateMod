package top.vmctcn.vmtu.mod.modpack.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ModpackInfoWriter {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final ModpackInfo modpackInfo = ModpackInfoReader.getModpackInfo();
    private static final Path gamePath = ModPlatform.getGameDir();
    private static final Path modpackInfoPath = gamePath.resolve("modpackinfo.json");

    public static void syncModpackVersion(String newVersion) {
        if (modpackInfo != null && modpackInfo.modpack != null) {
            String oldVersion = modpackInfo.modpack.version;
            modpackInfo.modpack.version = newVersion;

            try {
                Files.write(modpackInfoPath, GSON.toJson(modpackInfo).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                VMTranslationUpdate.LOGGER.info("Modpack version updated from {} to {}", oldVersion, newVersion);
            } catch (IOException e) {
                VMTranslationUpdate.LOGGER.error("Failed to update modpack version to {}", newVersion, e);
            }
        } else {
            VMTranslationUpdate.LOGGER.error("Cannot update modpack version: modpackInfo or modpack is null");
        }
    }
}