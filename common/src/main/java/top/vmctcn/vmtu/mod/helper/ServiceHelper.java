package top.vmctcn.vmtu.mod.helper;

import java.util.ServiceLoader;

public class ServiceHelper {
    public static <T> T loadService(final Class<T> clazz) {
        ServiceLoader<T> loader = ServiceLoader.load(clazz);
        for (T service : loader) {
            return service;
        }
        throw new AssertionError("No impl found for " + clazz.getPackage().getName());
    }
}
