package top.vmctcn.vmtranslationupdate.forge;

import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;

import java.util.function.Function;

public class ForgeHelper {
    public static void registerConfigScreen(String modid, Function<Screen, Screen> screenFunction) {
        ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow();
        modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> screenFunction.apply(screen)));
    }

    public static void getClientModIgnoredServerOnly(String modid) {
        ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow();
        modContainer.registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
    }
}
