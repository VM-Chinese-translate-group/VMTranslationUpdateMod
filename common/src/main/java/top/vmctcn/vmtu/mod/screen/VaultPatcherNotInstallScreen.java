package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.multiversion.Texts;

public class VaultPatcherNotInstallScreen extends AbstractNotInstallScreen {
    public VaultPatcherNotInstallScreen(Screen lastScreen) {
        super(
                lastScreen,
                "VaultPatcher",
                false
        );
    }

    @Override
    public void setConfigValue(boolean value) {
        ModConfigHelper.getConfig().modInstallCheck.vaultPatcher = value;
    }

    @Override
    public String getDownloadUrl() {
        return "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/";
    }

    @Override
    public MutableComponent getScreenDescription() {
        return Texts.translatable("vmtu.required_mod.not_install.desc", modName);
    }

    @Override
    public MutableComponent getCheckboxTooltip() {
        return Texts.translatable("vmtu.required_mod.not_install.checkbox.tooltip");
    }
}
