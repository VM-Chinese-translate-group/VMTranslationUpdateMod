package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class PackDownloadUtil {
    public static final MinecraftClient client = MinecraftClient.getInstance();
    public static final Path resourcePackDir = client.getResourcePackDir().toAbsolutePath();
    public static String resourcePackName = ModConfigUtil.getConfig().translationPackName + ".zip";
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

        // 检测下载完成后尝试自动设置资源包
        if (Files.exists(resPackFilePath) && ModConfigUtil.getConfig().autoInstallVMTranslationPack) {
            try {
                setResourcePack();
            } catch (Exception e) {
                VMTranslationUpdate.LOGGER.warn("Failed to set up \"%s\" resource pack.", resourcePackName.getBytes(StandardCharsets.UTF_8));
                e.printStackTrace();
            }
        }
    }

    public static void downloadResPackFile(Path savePath) throws IOException {
        URI uri = URI.create(ModConfigUtil.getConfig().translationPackUrl + ModConfigUtil.getConfig().translationPackName + ".zip");
        URLConnection connection = uri.toURL().openConnection();
        connection.setConnectTimeout(10000);
        
        try (InputStream in = uri.toURL().openStream()) {
            Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void setResourcePack() {
        MinecraftClient client = MinecraftClient.getInstance();
        GameOptions gameOptions = client.options;
        // 在 gameOptions 中加载资源包
        if (!gameOptions.resourcePacks.contains(resourcePackName)) {
            client.options.resourcePacks.add(resourcePackName);
        } else {
            List<String> packs = new ArrayList<>(100);
            // 资源包的 index 越小优先级越低（在资源包 GUI 中置于更低层）
            packs.add(resourcePackName);
            packs.addAll(gameOptions.resourcePacks);
            gameOptions.resourcePacks = packs;
        }
    }
}
