package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screens.Screen;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;

public class I18nUpdateNotInstallScreen extends AbstractNotInstallScreen {
    public I18nUpdateNotInstallScreen(Screen lastScreen) {
        super(
                lastScreen,
                "I18nUpdateMod",
                "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/",
                false
        );

        ModConfigHelper.getConfig().modInstallCheck.i18nUpdateMod = isSelectedCheckBox;
    }
}
