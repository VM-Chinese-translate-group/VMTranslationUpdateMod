package top.vmctcn.vmtu.mod.modpack.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.multiversion.util.ObjectUtils;

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
    private static final Path gamePath = ModPlatform.getGameDir();
    private static final Path modpackInfoPath = gamePath.resolve("modpackinfo.json");

    static {
        if (Files.exists(modpackInfoPath)) {
            try (Reader reader = Files.newBufferedReader(modpackInfoPath, StandardCharsets.UTF_8)) {
                modpackInfo = GSON.fromJson(reader, ModpackInfo.class);
                if (modpackInfo == null) {
                    if (ModConfigs.misc.generateExampleModpackInfo == true) {
                        ModContexts.LOGGER.warn("modpackinfo.json is empty or invalid, generating default file.");
                        generateDefaultModpackInfo();
                    } else {
                        ModContexts.LOGGER.warn("modpackinfo.json is empty or invalid, skip it.");
                    }
                }
            } catch (Exception e) {
                if (ModConfigs.misc.generateExampleModpackInfo == true) {
                    ModContexts.LOGGER.warn("Error reading modpackinfo.json, generating default file.", e);
                    generateDefaultModpackInfo();
                } else {
                    ModContexts.LOGGER.warn("Error reading modpackinfo.json, skip it.");
                }
            }
        } else {
            if (ModConfigs.misc.generateExampleModpackInfo == true) {
                ModContexts.LOGGER.warn("modpackinfo.json does not exist, generating default file.");
                generateDefaultModpackInfo();
            } else {
                ModContexts.LOGGER.warn("modpackinfo.json does not exist, skip it.");
            }
        }
    }

    private static void generateDefaultModpackInfo() {
        modpackInfo = new DefaultModpackInfo();

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
        return ObjectUtils.requireNonNullElseGet(modpackInfo, DefaultModpackInfo::new);
    }

    public static boolean isExampleModpackInfo() {
        return getModpackInfo().getModpack().getTranslation().getId().equals("example");
    }
}
