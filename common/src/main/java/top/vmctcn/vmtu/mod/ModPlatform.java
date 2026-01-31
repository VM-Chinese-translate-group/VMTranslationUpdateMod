package top.vmctcn.vmtu.mod;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class ModPlatform {

    @ExpectPlatform
    public static String getGameVersion() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Path getGameDir() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isDevelopmentEnvironment() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
}
