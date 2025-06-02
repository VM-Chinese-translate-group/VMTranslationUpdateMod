package top.vmctcn.vmtu.mod.helper;

import java.util.ServiceLoader;

public class ServiceHelper {
    public static <T> T loadService(final Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new AssertionError("No impl found for " + clazz.getPackageName()));
    }
}
