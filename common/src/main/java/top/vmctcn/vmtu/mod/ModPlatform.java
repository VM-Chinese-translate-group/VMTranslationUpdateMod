package top.vmctcn.vmtu.mod;

import top.vmctcn.vmtu.libraries.common.CommonContexts;

import java.nio.file.Path;

public interface ModPlatform {

    static ModPlatform getInstance() {
        return CommonContexts.loadService(ModPlatform.class);
    }

    String getGameVersion();

    Path getGameDir();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();
}
