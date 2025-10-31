package top.vmctcn.vmtu.mod.neoforge;

import com.google.auto.service.AutoService;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import top.vmctcn.vmtu.mod.ModPlatform;

import java.nio.file.Path;

@AutoService(ModPlatform.class)
public class ModPlatformImpl implements ModPlatform {
    @Override
    public String getGameVersion() {
        return FMLLoader.versionInfo().mcVersion();
    }

    @Override
    public Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }
}
