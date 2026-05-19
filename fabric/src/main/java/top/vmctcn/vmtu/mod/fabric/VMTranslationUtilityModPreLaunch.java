package top.vmctcn.vmtu.mod.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import top.vmctcn.vmtu.libraries.common.CommonContexts;
import top.vmctcn.vmtu.mod.VMTranslationUtilityMod;
import top.vmctcn.vmtu.multiversion.fabric.FabricUtils;

import java.nio.file.Path;

public class VMTranslationUtilityModPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        String gameVersion = FabricUtils.getGameVersion();
        Path gameDir = FabricLoader.getInstance().getGameDir();
        CommonContexts.setGameInfo(gameVersion, gameDir);

        VMTranslationUtilityMod.autoDownloadAndLoadPack();
    }
}
