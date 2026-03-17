package top.vmctcn.vmtu.multiversion.util;

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectUtils {
    public static <T> T requireNonNullElseGet(T obj, Supplier<? extends T> supplier) {
        return (obj != null) ? obj : Objects.requireNonNull(Objects.requireNonNull(supplier, "supplier").get(), "supplier.get()");
    }
}
