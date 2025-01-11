package top.vmctcn.vmtucore.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import top.vmctcn.vmtucore.VMTUCore;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * Under AGPL License
 *
 * @author CFPAOrg/I18nUpdateMod3 (xfl03)
 */
public class AssetUtil {
    public static void download(String url, Path localFile) throws IOException, URISyntaxException {
        VMTUCore.LOGGER.info("Downloading: {} -> {}", url, localFile);
        FileUtils.copyURLToFile(new URI(url).toURL(), localFile.toFile(),
                (int) TimeUnit.SECONDS.toMillis(3), (int) TimeUnit.SECONDS.toMillis(33));
        VMTUCore.LOGGER.debug("Downloaded: {} -> {}", url, localFile);
    }

    public static String getString(String url) throws IOException, URISyntaxException {
        return IOUtils.toString(new URI(url).toURL(), StandardCharsets.UTF_8);
    }
}
