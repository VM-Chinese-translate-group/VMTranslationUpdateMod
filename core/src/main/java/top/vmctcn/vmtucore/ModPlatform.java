package top.vmctcn.vmtucore;

import top.vmctcn.vmtucore.util.ServiceHelper;

import java.nio.file.Path;

public interface ModPlatform {
    ModPlatform INSTANCE = ServiceHelper.loadService(ModPlatform.class);

    String getGameVersion();

    Path getGameDir();
}
