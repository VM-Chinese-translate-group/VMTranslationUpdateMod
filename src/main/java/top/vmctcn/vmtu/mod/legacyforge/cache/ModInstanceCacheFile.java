package top.vmctcn.vmtu.mod.legacyforge.cache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModInstanceCacheFile {
    private static final String CACHE_FILE_NAME = "instance_cache.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ModInstanceCache INSTANCE;

    public static ModInstanceCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    private static Path getCacheFilePath() {
        return ModPlatform.getGameDir().resolve(".vmtu").resolve(CACHE_FILE_NAME);
    }

    public static ModInstanceCache load() {
        Path cacheFile = getCacheFilePath();

        if (Files.exists(cacheFile)) {
            try {
                String json = new String(Files.readAllBytes(cacheFile));
                ModInstanceCache cache = GSON.fromJson(json, ModInstanceCache.class);
                if (cache != null) {
                    ModContexts.LOGGER.info("Loaded mod cache from {}", cacheFile);
                    return cache;
                }
            } catch (IOException e) {
                ModContexts.LOGGER.error("Failed to load mod cache from {}", cacheFile, e);
            }
        }

        // Return default instance if file doesn't exist or failed to load
        ModContexts.LOGGER.info("Creating default mod cache");
        return new ModInstanceCache();
    }

    public static void save() {
        Path cacheFile = getCacheFilePath();

        try {
            // Ensure parent directory exists
            Files.createDirectories(cacheFile.getParent());

            String json = GSON.toJson(getInstance());
            Files.write(cacheFile, json.getBytes());
            ModContexts.LOGGER.info("Saved mod cache to {}", cacheFile);
        } catch (IOException e) {
            ModContexts.LOGGER.error("Failed to save mod cache to {}", cacheFile, e);
        }
    }
}
