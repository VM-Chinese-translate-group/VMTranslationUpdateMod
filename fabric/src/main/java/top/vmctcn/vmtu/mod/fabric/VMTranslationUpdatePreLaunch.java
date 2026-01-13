package top.vmctcn.vmtu.mod.fabric;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import top.vmctcn.vmtu.mod.helper.GameOptionsHelper;

public class VMTranslationUpdatePreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        GameOptionsHelper.autoDownloadAndLoadPack();
    }
}
