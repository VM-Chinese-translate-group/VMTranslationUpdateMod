package top.vmctcn.vmtucore.forge;

import com.google.auto.service.AutoService;
import com.google.gson.JsonObject;
import cpw.mods.modlauncher.Launcher;
import net.minecraftforge.fml.loading.FMLPaths;
import top.vmctcn.vmtucore.ModPlatform;
import top.vmctcn.vmtucore.VMTUCore;
import top.vmctcn.vmtucore.util.ReflectionHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

@AutoService(ModPlatform.class)
public class ModPlatformImpl implements ModPlatform {
    @Override
    public String getGameVersion() {
        // MinecraftForge 1.13~1.20.2
        // NeoForge 1.20.1~
        try {
            String[] args = (String[]) ReflectionHelper.clazz(Launcher.INSTANCE).get("argumentHandler").get("args").get();
            for (int i = 0; i < args.length - 1; ++i) {
                if (args[i].equalsIgnoreCase("--fml.mcversion")) {
                    return args[i + 1];
                }
            }
        } catch (Exception e) {
            VMTUCore.LOGGER.warn("Error getting minecraft version: %s", e);
        }

        // MinecraftForge 1.20.3~
        // 1.20.3: https://github.com/MinecraftForge/MinecraftForge/blob/1.20.x/fmlloader/src/main/java/net/minecraftforge/fml/loading/VersionInfo.java
        try {
            Class<?> clazz = Class.forName("net.minecraftforge.fml.loading.FMLLoader");
            try (InputStream is = clazz.getResourceAsStream("/forge_version.json")) {
                return VMTUCore.GSON.fromJson(new InputStreamReader(is), JsonObject.class).get("mc").getAsString();
            }
        } catch (Exception e) {
            VMTUCore.LOGGER.warn("Error getting minecraft version: %s", e);
        }
        return null;
    }

    @Override
    public Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }
}
