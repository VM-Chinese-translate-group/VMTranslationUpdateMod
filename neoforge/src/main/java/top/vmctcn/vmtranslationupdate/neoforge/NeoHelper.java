package top.vmctcn.vmtranslationupdate.neoforge;

import net.minecraft.client.gui.screen.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Function;

public class NeoHelper {
    public static void registerConfigScreen(ModContainer modContainer, Function<Screen, Screen> screenFunction) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (client, screen) -> screenFunction.apply(screen));
    }
}
