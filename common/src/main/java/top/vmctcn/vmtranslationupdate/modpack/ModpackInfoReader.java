package top.vmctcn.vmtranslationupdate.modpack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtucore.ModPlatform;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ModpackInfoReader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ModpackInfo modpackInfo;
    private static final Path gamePath = ModPlatform.INSTANCE.getGameDir();
    private static final Path modpackInfoPath = gamePath.resolve("modpackinfo.json");

    static {
        init();
    }

    public static void init() {
        if (Files.exists(modpackInfoPath)) {
            try (Reader reader = Files.newBufferedReader(modpackInfoPath, StandardCharsets.UTF_8)) {
                modpackInfo = GSON.fromJson(reader, ModpackInfo.class);
                if (modpackInfo == null) {
                    VMTranslationUpdate.LOGGER.warn("modpackinfo.json is empty or invalid, generating default file.");
                    generateDefaultModpackInfo();
                }
            } catch (Exception e) {
                VMTranslationUpdate.LOGGER.warn("Error reading modpackinfo.json, generating default file.", e);
                generateDefaultModpackInfo();
            }
        } else {
            VMTranslationUpdate.LOGGER.warn("modpackinfo.json does not exist, generating default file.");
            generateDefaultModpackInfo();
        }
    }

    private static void generateDefaultModpackInfo() {
        modpackInfo = new ModpackInfo();
        modpackInfo.modpack = new ModpackInfo.Modpack();
        modpackInfo.modpack.name = "ExampleModpack";
        modpackInfo.modpack.version = "v0.1.0";

        modpackInfo.modpack.translation = new ModpackInfo.Translation();
        modpackInfo.modpack.translation.url = "https://vmct-cn.top/modpacks/example/";
        modpackInfo.modpack.translation.language = "zh_cn";
        modpackInfo.modpack.translation.version = "1.0.0";
        modpackInfo.modpack.translation.updateCheckUrl = "https://gitee.com/Wulian233/vmtu/raw/main/update/example.txt";
        modpackInfo.modpack.translation.resourcePackName = "VM汉化组模组汉化包1.19及以上";

        try {
            Files.writeString(modpackInfoPath, GSON.toJson(modpackInfo), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            VMTranslationUpdate.LOGGER.info("Default modpackinfo.json generated.");

            // 再次读取以确保正确加载
            try (Reader reader = Files.newBufferedReader(modpackInfoPath, StandardCharsets.UTF_8)) {
                modpackInfo = GSON.fromJson(reader, ModpackInfo.class);
            }
        } catch (IOException e) {
            VMTranslationUpdate.LOGGER.error("Failed to generate default modpackinfo.json", e);
        }
    }

    public static ModpackInfo getModpackInfo() {
        return modpackInfo;
    }
}