package top.vmctcn.vmtranslationupdate.neoforge;

import net.minecraft.client.gui.screen.Screen;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.ConfigScreenHandler;

import java.util.function.Function;

public class NeoHelper {
    public static void registerConfigScreen(String modid, Function<Screen, Screen> screenFunction) {
        ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow();
        modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> screenFunction.apply(screen)));
    }

    public static void getClientModIgnoredServerOnly(String modid) {
        ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow();
        modContainer.registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
    }
}
