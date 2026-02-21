package top.vmctcn.vmtu.mod.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlUtils {
    public static URL getUrl(String stringUrl) {
        try {
            return new URI(stringUrl).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            return null;
        }
    }

    public static String getCurseForgeModFileUrl(String slug) {
        return String.format("https://www.curseforge.com/minecraft/mc-mods/%s/files/", slug);
    }
}
