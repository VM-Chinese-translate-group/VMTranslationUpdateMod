package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screens.Screen;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;

public class VaultPatcherNotInstallScreen extends AbstractNotInstallScreen {
    public VaultPatcherNotInstallScreen(Screen lastScreen) {
        super(
                lastScreen,
                "VaultPatcher",
                "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/",
                false
        );

        ModConfigHelper.getConfig().vaultPatcherCheck = isSelectedCheckBox;
    }
}
