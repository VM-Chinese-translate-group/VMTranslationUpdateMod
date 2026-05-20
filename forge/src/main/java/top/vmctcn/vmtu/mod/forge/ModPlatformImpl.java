package top.vmctcn.vmtu.mod.forge;

import com.google.auto.service.AutoService;
import cpw.mods.modlauncher.Launcher;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import top.vmctcn.vmtu.libraries.resourcepack.util.Reflection;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.multiversion.forge.ForgeUtils;
import top.vmctcn.vmtu.mod.ModPlatform;

import java.nio.file.Path;

@AutoService(ModPlatform.class)
public class ModPlatformImpl implements ModPlatform {
    @Override
    public String getGameVersion() {
        return ForgeUtils.getVersionInfo().mcVersion();
    }

    @Override
    public Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FMLLoader.getLoadingModList().getModFileById(modId) != null;
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return ForgeUtils.isDevelopmentEnvironment();
    }
}
