package top.vmctcn.vmtu.mod;

import top.vmctcn.vmtu.mod.helper.ServiceHelper;

import java.nio.file.Path;

public interface ModPlatform {
    ModPlatform INSTANCE = ServiceHelper.loadService(ModPlatform.class);

    String getGameVersion();

    Path getGameDir();
}
