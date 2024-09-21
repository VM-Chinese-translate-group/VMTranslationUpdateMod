package top.vmctcn.vmtranslationupdate.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
    public static String getContentFromURL(String urlString) throws Exception {
        URI uri = URI.create(urlString);
        URLConnection connection = uri.toURL().openConnection();
        connection.setConnectTimeout(10000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(uri.toURL().openStream(), StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }
}
