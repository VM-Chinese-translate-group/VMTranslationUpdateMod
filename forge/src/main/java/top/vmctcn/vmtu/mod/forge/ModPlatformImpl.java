package top.vmctcn.vmtu.mod.forge;

import com.google.auto.service.AutoService;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
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
