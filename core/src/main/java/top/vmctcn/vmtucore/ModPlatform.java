package top.vmctcn.vmtucore;

import top.vmctcn.vmtucore.util.ServiceHelper;

public interface ModPlatform {
    ModPlatform INSTANCE = ServiceHelper.loadService(ModPlatform.class);

    String getGameVersion();
}
