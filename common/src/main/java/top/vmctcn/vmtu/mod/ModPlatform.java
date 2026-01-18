package top.vmctcn.vmtu.mod;

import java.nio.file.Path;

public interface ModPlatform {
    ModPlatform INSTANCE = ModContexts.loadService(ModPlatform.class);

    String getGameVersion();

    Path getGameDir();

    boolean isModLoaded(String modId);
}
