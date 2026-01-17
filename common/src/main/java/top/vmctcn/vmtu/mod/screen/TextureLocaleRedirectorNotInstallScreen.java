package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.multiversion.Texts;

//? if >=1.20.1 {
public class TextureLocaleRedirectorNotInstallScreen extends AbstractNotInstallScreen {
    public TextureLocaleRedirectorNotInstallScreen(Screen lastScreen) {
        super(
                lastScreen,
                "TextureLocaleRedirector",
                true
        );
    }

    @Override
    public void setConfigValue(boolean value) {
        ModConfigHelper.getConfig().modInstallCheck.textureLocaleRedirector = value;
    }

    @Override
    public String getDownloadUrl() {
        return "https://www.curseforge.com/minecraft/mc-mods/texture-locale-redirector/files/";
    }

    @Override
    public MutableComponent getScreenDescription() {
        return Texts.translatable("vmtu.required_mod.not_install.desc.tlr", modName);
    }

    @Override
    public MutableComponent getCheckboxTooltip() {
        return Texts.translatable("vmtu.required_mod.not_install.checkbox.tooltip.tlr");
    }
}
//?}
