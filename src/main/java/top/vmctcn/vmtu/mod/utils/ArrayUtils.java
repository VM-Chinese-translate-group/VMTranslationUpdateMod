package top.vmctcn.vmtu.mod.utils;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ArrayUtils {
    @SafeVarargs
    public static <T> T[] of(T... objects) {
        return objects;
    }

    public static <T> ArrayList<T> asArrayList(Stream<T> stream) {
        return stream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
