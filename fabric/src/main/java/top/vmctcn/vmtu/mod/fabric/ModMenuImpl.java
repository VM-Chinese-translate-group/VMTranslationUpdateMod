package top.vmctcn.vmtu.mod.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfigHelper::setConfigScreen;
    }
}