package top.vmctcn.vmtu.multiversion.forge;

import net.minecraft.client.gui.screens.Screen;
//? if >=1.19.2 {
/*import net.minecraftforge.client.ConfigScreenHandler;
*///?} else if 1.18.2 {
import net.minecraftforge.client.ConfigGuiHandler;
//?}
//? if >=1.18.2 {
import net.minecraftforge.fml.IExtensionPoint;
//?} else {
/*import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import java.util.NoSuchElementException;
*///?}
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
//? if >=1.18.2 {
import net.minecraftforge.fml.loading.VersionInfo;
//?}

import java.util.function.Function;

public class ForgeUtils {
    public static void registerConfigScreen(String modid, Function<Screen, Screen> screenFunction) {
        //? if >=1.18.2 {
        ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow();
        //?} else {
        /*ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow(() -> new NoSuchElementException("No value present"));
        *///?}

        //? if >=1.19.2 {
        /*modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> screenFunction.apply(screen)));
        *///?} else if 1.18.2 {
        modContainer.registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((client, screen) -> screenFunction.apply(screen)));
        //?} else {
        /*modContainer.registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (client, screen) -> screenFunction.apply(screen));
        *///?}
    }

    public static void getClientModIgnoredServerOnly(String modid) {
        //? if >=1.18.2 {
        ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow();
        //?} else {
        /*ModContainer modContainer = ModList.get().getModContainerById(modid).orElseThrow(() -> new NoSuchElementException("No value present"));
        *///?}

        //? if >=1.18.2 {
        modContainer.registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(IExtensionPoint.DisplayTest.IGNORESERVERONLY, (remoteVersion, isFromServer) -> true));
        //?} else {
        /*modContainer.registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (remoteVersion, isFromServer) -> true));
        *///?}
    }

    public static boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    public static VersionInfo getVersionInfo() {
        //? if >=1.18.2 {
        return FMLLoader.versionInfo();
        //?} else {
        /*return new VersionInfo();
        *///?}
    }
}
