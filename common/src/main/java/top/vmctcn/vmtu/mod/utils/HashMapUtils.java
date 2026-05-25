package top.vmctcn.vmtu.mod.utils;

import java.util.HashMap;

public class HashMapUtils {
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public static <K, V> HashMap<K, V> newHashMap(int numMappings) {
        if (numMappings < 0) {
            throw new IllegalArgumentException("Negative number of mappings: " + numMappings);
        }
        return new HashMap<>(calculateHashMapCapacity(numMappings));
    }

    static int calculateHashMapCapacity(int numMappings) {
        return (int) Math.ceil(numMappings / (double) DEFAULT_LOAD_FACTOR);
    }
}
