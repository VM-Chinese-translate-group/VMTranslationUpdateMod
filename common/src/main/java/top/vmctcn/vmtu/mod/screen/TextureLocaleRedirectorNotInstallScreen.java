package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screens.Screen;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;

//? if >=1.20.1 {
public class TextureLocaleRedirectorNotInstallScreen extends AbstractNotInstallScreen {
    public TextureLocaleRedirectorNotInstallScreen(Screen lastScreen) {
        super(
                lastScreen,
                "TextureLocaleRedirector",
                "https://www.curseforge.com/minecraft/mc-mods/texture-locale-redirector/files/",
                true
        );

        ModConfigHelper.getConfig().textureLocaleRedirectorCheck = isSelectedCheckBox;
    }
}
//?}
