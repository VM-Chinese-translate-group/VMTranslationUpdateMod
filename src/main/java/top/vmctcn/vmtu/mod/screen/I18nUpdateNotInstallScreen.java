package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import top.vmctcn.vmtu.multiversion.Texts;

public class I18nUpdateNotInstallScreen extends AbstractNotInstallScreen {
    public I18nUpdateNotInstallScreen(Screen lastScreen) {
        super(lastScreen, "I18nUpdateMod");
    }

    @Override
    public String getDownloadUrl() {
        return "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/";
    }

    @Override
    public Text getScreenDescription() {
        return Texts.translatable("vmtu.required_mod.not_install.desc", modName);
    }
}
