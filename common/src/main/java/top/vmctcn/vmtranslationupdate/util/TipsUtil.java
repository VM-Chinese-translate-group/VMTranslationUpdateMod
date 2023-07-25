package top.vmctcn.vmtranslationupdate.util;

import top.vmctcn.vmtranslationupdate.config.ConfigScreen;

import top.vmctcn.vmtranslationupdate.VMTranslationUpdateMod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TipsUtil {
    public static final List<String> messagesList = new ArrayList<>();
    public static String getRandomMessageFromURL(String tipsUrl) {
        if (messagesList.isEmpty()) {
            loadMessagesFromURL(tipsUrl);
        }
        if (!messagesList.isEmpty()) {
            int index = VMTranslationUpdateMod.random.nextInt(messagesList.size());
            return messagesList.get(index);
        }
        return null;
    }

    public static void loadMessagesFromURL(String tipsUrl) {
        try {
            URL url = new URL(tipsUrl);
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

    public static Integer getMinutes() {
        ConfigUtil.getConfig();
        return ConfigScreen.minutes;
    }
    public static String getTipsUrl() {
        ConfigUtil.getConfig();
        return ConfigScreen.tipsUrl;
    }
}
