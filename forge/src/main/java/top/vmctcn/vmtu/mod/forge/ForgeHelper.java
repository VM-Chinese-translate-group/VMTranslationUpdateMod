package top.vmctcn.vmtu.mod.forge;

import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class ForgeHelper {
    public static void registerConfigScreen(String modid, Function<Screen, Screen> screenFunction) {
        ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow(() -> new NoSuchElementException("No value present"));
        modContainer.registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (client, screen) -> screenFunction.apply(screen));
    }

    public static void getClientModIgnoredServerOnly(String modid) {
        ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow(() -> new NoSuchElementException("No value present"));
        modContainer.registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
