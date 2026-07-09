package top.vmctcn.vmtu.mod.legacyforge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.VMTranslationUpdateMod;
import top.vmctcn.vmtu.mod.command.ModCommand;

@Mod(modid = ModContexts.MOD_ID, name = ModContexts.MOD_NAME, clientSideOnly = true)
public class VMTranslationUpdateLegacyForge {
    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        VMTranslationUpdateMod.init();
        VMTranslationUpdateMod.autoDownloadAndLoadPack();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public static void onInit(FMLInitializationEvent event) {
        ClientCommandHandler.instance.register(new ModCommand());

        Minecraft.getInstance().execute(() -> {
            try {
                Minecraft.getInstance().reloadResources();
                ModContexts.LOGGER.info("Resources refreshed after loading translation pack");
            } catch (Exception e) {
                ModContexts.LOGGER.error("Failed to refresh resources", e);
            }
        });
    }
}
