package top.vmctcn.vmtu.mod;

import java.nio.file.Path;

public interface ModPlatform {

    static ModPlatform getInstance() {
        return ModContexts.loadService(ModPlatform.class);
    }

    String getGameVersion();

    Path getGameDir();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();
}
