package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.config.ModConfigs;

public class ModConfigScreen extends GuiConfig {
    public ModConfigScreen(Screen parentScreen) {
        super(
                parentScreen,
                new ConfigElement(ModConfigs.config.getCategory(ModConfigs.categoryKey)).getChildElements(),
                VMTranslationUpdate.MOD_ID,
                false,
                false,
                new TranslatableText("config.vmtranslationupdate.title").getContent()
        );
    }
}
