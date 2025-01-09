package top.vmctcn.vmtranslationupdate.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class TipsUtil {
    public static final List<String> messagesList = new ArrayList<>();
    public static Random random = new Random();

    public static void loadMessagesFromURL(String tipsUrl) {
        try {
            String content = HttpUtil.getContentFromURL(tipsUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes()), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                messagesList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<String> getRandomMessageFromURLAsync(String tipsUrl) {
        if (messagesList.isEmpty()) {
            return CompletableFuture.supplyAsync(() -> {
                loadMessagesFromURL(tipsUrl);
                int index = random.nextInt(messagesList.size());
                return messagesList.get(index);
            });
        } else {
            int index = random.nextInt(messagesList.size());
            return CompletableFuture.completedFuture(messagesList.get(index));
        }
    }

    public static Integer getTipsMinutes() {
        return ModConfigUtil.getConfig().tipsMinutes;
    }
}
