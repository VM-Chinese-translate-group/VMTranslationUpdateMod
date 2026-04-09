package top.vmctcn.vmtu.multiversion.neoforge;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.fml.ModContainer;
//? if 1.20.4 {
/*import net.neoforged.fml.IExtensionPoint;
*///?}
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.VersionInfo;
//? if >=1.20.6 {
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//?} else {
/*import net.neoforged.neoforge.client.ConfigScreenHandler;
*///?}

import java.util.function.Function;

public class NeoUtils {
    public static void registerConfigScreen(ModContainer modContainer, Function<Screen, Screen> screenFunction) {
        //? if >=1.20.6 {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, screen) -> screenFunction.apply(screen));
        //?} else {
        /*modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> screenFunction.apply(screen)));
        *///?}
    }

    //? if 1.20.4 {
    /*public static void getClientModIgnoredServerOnly(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
    }
    *///?}

    public static Dist getDist() {
        //? if >=1.21.10 {
        return FMLLoader.getCurrent().getDist();
        //?} else {
        /*return FMLLoader.getDist();
        *///?}
    }

    public static VersionInfo getVersionInfo() {
        //? if >=1.21.10 {
        return FMLLoader.getCurrent().getVersionInfo();
        //?} else {
        /*return FMLLoader.versionInfo();
        *///?}
    }

    public static LoadingModList getLoadingModList() {
        //? if >=1.21.10 {
        return FMLLoader.getCurrent().getLoadingModList();
        //?} else {
        /*return FMLLoader.getLoadingModList();
        *///?}
    }

    public static boolean isDevelopmentEnvironment() {
        //? if >=1.21.10 {
        return !FMLLoader.getCurrent().isProduction();
        //?} else {
        /*return !FMLLoader.isProduction();
         *///?}
    }
}
