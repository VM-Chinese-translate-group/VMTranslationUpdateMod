package top.vmctcn.vmtu.mod;

import java.nio.file.Path;

public interface ModPlatform {
    ModPlatform INSTANCE = ServiceHelper.loadService(ModPlatform.class);

    String getGameVersion();

    Path getGameDir();
}
