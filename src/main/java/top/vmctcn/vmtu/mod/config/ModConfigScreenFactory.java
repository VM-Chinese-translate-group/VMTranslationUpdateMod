package top.vmctcn.vmtu.mod.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.screen.ModConfigScreen;

public class ModConfigScreenFactory extends DefaultGuiFactory {
    public ModConfigScreenFactory() {
        super(VMTranslationUpdate.MOD_ID, new TranslatableText("config.vmtranslationupdate.title").getContent());
    }

    @Override
    public Screen createConfigGui(Screen parentScreen) {
        return new ModConfigScreen(parentScreen);
    }
}
