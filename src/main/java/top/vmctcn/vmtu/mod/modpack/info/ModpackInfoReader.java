package top.vmctcn.vmtu.mod.modpack.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ModpackInfoReader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ModpackInfo modpackInfo;
    private static boolean initialized = false;

    private static void ensureInitialized() {
        if (initialized) {
            return;
        }
        initialized = true;

        Path gamePath = ModPlatform.getGameDir();
        if (gamePath == null) {
            ModContexts.LOGGER.error("Game directory is null, cannot initialize ModpackInfoReader");
            generateDefaultModpackInfoWithoutFile();
            return;
        }
        Path modpackInfoPath = gamePath.resolve("modpackinfo.json");

        if (Files.exists(modpackInfoPath)) {
            try (Reader reader = Files.newBufferedReader(modpackInfoPath, StandardCharsets.UTF_8)) {
                modpackInfo = GSON.fromJson(reader, ModpackInfo.class);
                if (modpackInfo == null) {
                    ModContexts.LOGGER.warn("modpackinfo.json is empty or invalid, generating default file.");
                    generateDefaultModpackInfo(modpackInfoPath);
                }
            } catch (Exception e) {
                ModContexts.LOGGER.warn("Error reading modpackinfo.json, generating default file.", e);
                generateDefaultModpackInfo(modpackInfoPath);
            }
        } else {
            ModContexts.LOGGER.warn("modpackinfo.json does not exist, generating default file.");
            generateDefaultModpackInfo(modpackInfoPath);
        }
    }

    private static void generateDefaultModpackInfoWithoutFile() {
        modpackInfo = new ModpackInfo();
        modpackInfo.modpack = new ModpackInfo.Modpack();
        modpackInfo.modpack.name = "ExampleModpack";
        modpackInfo.modpack.version = "0.1.0";

        modpackInfo.modpack.translation = new ModpackInfo.Translation();
        modpackInfo.modpack.translation.id = "example";
        modpackInfo.modpack.translation.url = "https://vmct-cn.top/modpacks/example/";
        modpackInfo.modpack.translation.language = "zh_cn";
        modpackInfo.modpack.translation.version = "1.0.0";
    }

    private static void generateDefaultModpackInfo(Path modpackInfoPath) {
        generateDefaultModpackInfoWithoutFile();

        try {
            try (Writer writer = Files.newBufferedWriter(modpackInfoPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                writer.write(GSON.toJson(modpackInfo));
            }
            ModContexts.LOGGER.info("Default modpackinfo.json generated.");

            // 再次读取以确保正确加载
            try (Reader reader = Files.newBufferedReader(modpackInfoPath, StandardCharsets.UTF_8)) {
                modpackInfo = GSON.fromJson(reader, ModpackInfo.class);
            }
        } catch (IOException e) {
            ModContexts.LOGGER.error("Failed to generate default modpackinfo.json", e);
        }
    }

    public static ModpackInfo getModpackInfo() {
        ensureInitialized();
        return modpackInfo;
    }
}
