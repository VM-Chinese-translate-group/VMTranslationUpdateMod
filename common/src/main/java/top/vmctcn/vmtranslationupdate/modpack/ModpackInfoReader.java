package top.vmctcn.vmtranslationupdate.modpack;

import com.google.gson.Gson;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtucore.ModPlatform;

import java.io.FileReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ModpackInfoReader {
    private static final Gson GSON = new Gson();
    private static ModpackInfo modpackInfo;
    private static final Path gamePath = ModPlatform.INSTANCE.getGameDir();

    static {
        init();
    }

    public static void init() {
        Path modpackInfoPath = gamePath.resolve("modpackinfo.json");
        try (Reader reader = new FileReader(modpackInfoPath.toFile(), StandardCharsets.UTF_8)) {
            modpackInfo = GSON.fromJson(reader, ModpackInfo.class);
        } catch (Exception e) {
            VMTranslationUpdate.LOGGER.warn("Error getting modpack info index: ", e);
        }
    }

    public static ModpackInfo getModpackInfo() {
        return modpackInfo;
    }
}
