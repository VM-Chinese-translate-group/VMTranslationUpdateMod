package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.config.ModConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class PackDownloadUtil {
    private static final Minecraft client = Minecraft.getMinecraft();
    public static final Path resourcePackDir = client.getResourcePackRepository().getDirResourcepacks().toPath();
    public static String resourcePackName = ModConfig.packName + ".zip";
    private static final Path resPackFilePath = resourcePackDir.resolve(resourcePackName);

    public static void downloadResPack() {
        // 检查游戏下资源包目录
        if (!Files.isDirectory(resourcePackDir)) {
            try {
                Files.createDirectories(resourcePackDir);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        // 开始下载资源包
        try {
            downloadResPackFile(resPackFilePath);
        } catch (Exception e) {
            VMTranslationUpdate.LOGGER.warn("Failed to download \"%s\" resource pack.", resourcePackName.getBytes(StandardCharsets.UTF_8));
            e.printStackTrace();
        }

        if (ModConfig.autoLoadResPack) {
            // 检测下载完成后尝试自动设置资源包
            if (Files.exists(resPackFilePath)) {
                try {
                    setResourcePack();
                } catch (Exception e) {
                    VMTranslationUpdate.LOGGER.warn("Failed to set up \"%s\" resource pack.", resourcePackName.getBytes(StandardCharsets.UTF_8));
                    e.printStackTrace();
                }
            }
        }


    }

    public static void downloadResPackFile(Path savePath) throws IOException {
        URL url = new URL(ModConfig.packUrl + ".zip");
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(10000);

        try (InputStream in = url.openStream()) {
            Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void setResourcePack() {
        Minecraft client = Minecraft.getMinecraft();
        GameSettings gameSettings = client.gameSettings;
        // 在 gameSettings 中加载资源包
        if (!gameSettings.resourcePacks.contains(resourcePackName)) {
            client.gameSettings.resourcePacks.add(resourcePackName);
        } else {
            List<String> packs = new ArrayList<>(100);
            // 资源包的 index 越小优先级越低（在资源包 GUI 中置于更低层）
            packs.add(resourcePackName);
            packs.addAll(gameSettings.resourcePacks);
            gameSettings.resourcePacks = packs;
        }
    }
}
