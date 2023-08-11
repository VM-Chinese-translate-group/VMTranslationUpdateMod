package top.vmctcn.vmtranslationupdate.util;

import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class PackUtil {
    public static final Path saveDirectory = MinecraftClient.getInstance().getResourcePackDir().toPath();
    public static String packName = ConfigUtil.getConfig().packName;
    public static void Download() {
        LocalDate currentDate = LocalDate.now();
        // 检查今天是否是星期六
        if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            try {
                Path zipFilePath = saveDirectory.resolve(packName);
                downloadFile(zipFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void downloadFile(Path savePath) throws IOException {
        URL url = new URL(ConfigUtil.getConfig().packUrl);
        try (InputStream in = url.openStream()) {
            Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
