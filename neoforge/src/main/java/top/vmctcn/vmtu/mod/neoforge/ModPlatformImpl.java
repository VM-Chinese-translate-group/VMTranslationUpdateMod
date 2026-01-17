package top.vmctcn.vmtu.mod.neoforge;

import com.google.auto.service.AutoService;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.multiversion.neoforge.NeoUtils;

import java.nio.file.Path;

@AutoService(ModPlatform.class)
public class ModPlatformImpl implements ModPlatform {
    @Override
    public String getGameVersion() {
        return NeoUtils.getVersionInfo().mcVersion();
    }

    @Override
    public Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public boolean isModLoaded(String modId) {
        return NeoUtils.getLoadingModList().getModFileById(modId) != null;
    }
}
