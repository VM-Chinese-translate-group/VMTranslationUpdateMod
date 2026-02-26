package top.vmctcn.vmtu.mod.modpack.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ModpackInfoWriter {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void syncModpackVersion(String newVersion) {
        ModpackInfo modpackInfo = ModpackInfoReader.getModpackInfo();
        if (modpackInfo != null && modpackInfo.modpack != null) {
            String oldVersion = modpackInfo.modpack.version;
            modpackInfo.modpack.version = newVersion;

            Path gamePath = ModPlatform.getGameDir();
            if (gamePath == null) {
                ModContexts.LOGGER.error("Game directory is null, cannot sync modpack version");
                return;
            }
            Path modpackInfoPath = gamePath.resolve("modpackinfo.json");

            try {
                Files.write(modpackInfoPath, GSON.toJson(modpackInfo).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                ModContexts.LOGGER.info("Modpack version updated from {} to {}", oldVersion, newVersion);
            } catch (IOException e) {
                ModContexts.LOGGER.error("Failed to update modpack version to {}", newVersion, e);
            }
        } else {
            ModContexts.LOGGER.error("Cannot update modpack version: modpackInfo or modpack is null");
        }
    }
}