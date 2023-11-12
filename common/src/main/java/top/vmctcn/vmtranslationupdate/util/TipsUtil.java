package top.vmctcn.vmtranslationupdate.util;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TipsUtil {
    public static final List<String> messagesList = new ArrayList<>();
    public static String getRandomMessageFromURL(String tipsUrl) {
        if (messagesList.isEmpty()) {
            loadMessagesFromURL(tipsUrl);
        }
        if (!messagesList.isEmpty()) {
            int index = VMTranslationUpdate.random.nextInt(messagesList.size());
            return messagesList.get(index);
        }
        return null;
    }

    public static void loadMessagesFromURL(String tipsUrl) {
        try {
            URL url = new URL(tipsUrl);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(10000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
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
                int index = VMTranslationUpdate.random.nextInt(messagesList.size());
                return messagesList.get(index);
            });
        } else {
            int index = VMTranslationUpdate.random.nextInt(messagesList.size());
            return CompletableFuture.completedFuture(messagesList.get(index));
        }
    }

    public static Integer getMinutes() {
        return ModConfigUtil.getConfig().minutes;
    }
}
