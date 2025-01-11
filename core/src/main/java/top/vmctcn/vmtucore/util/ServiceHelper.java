package top.vmctcn.vmtucore.util;

import java.util.ServiceLoader;

/**
 * Under AGPL License
 *
 * @author TexTrue
 */
public class ServiceHelper {
    public static <T> T loadService(final Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new AssertionError("No impl found for " + clazz.getPackageName()));
    }
}
