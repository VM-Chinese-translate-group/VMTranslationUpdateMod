package top.vmctcn.vmtu.mod.legacyforge;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;
import top.vmctcn.vmtu.mod.command.ModCommand;

@Mod(modid = VMTranslationUpdate.MOD_ID, name = VMTranslationUpdate.MOD_NAME, clientSideOnly = true)
public class VMTranslationUpdateLegacyForge {
    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(this);

        VMTranslationUpdate.init();
    }

    @Mod.EventHandler
    public static void onInit(FMLInitializationEvent event) {
        VMTranslationUpdate.autoDownloadAndLoadPack();

        ClientCommandHandler.instance.register(new ModCommand());
    }
}
