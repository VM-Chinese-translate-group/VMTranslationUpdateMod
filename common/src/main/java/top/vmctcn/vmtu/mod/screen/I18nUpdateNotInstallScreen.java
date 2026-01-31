package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.multiversion.Texts;

public class I18nUpdateNotInstallScreen extends AbstractNotInstallScreen {
    public I18nUpdateNotInstallScreen(Screen lastScreen) {
        super(lastScreen, "I18nUpdateMod", false);
    }

    @Override
    public void setModInstallCheck(boolean value) {
        ModConfigHelper.getConfig().modInstallCheck.i18nUpdateMod = value;
    }

    @Override
    public String getDownloadUrl() {
        return "https://www.curseforge.com/minecraft/mc-mods/i18nupdatemod/files/";
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
