package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import top.vmctcn.vmtu.multiversion.Texts;

public class VaultPatcherNotInstallScreen extends AbstractNotInstallScreen {
    public VaultPatcherNotInstallScreen(Screen lastScreen) {
        super(lastScreen, "VaultPatcher");
    }

    @Override
    public String getDownloadUrl() {
        return "https://www.curseforge.com/minecraft/mc-mods/vault-patcher/files/";
    }

    @Override
    public Text getScreenDescription() {
        return Texts.translatable("vmtu.required_mod.not_install.desc", modName);
    }
}
