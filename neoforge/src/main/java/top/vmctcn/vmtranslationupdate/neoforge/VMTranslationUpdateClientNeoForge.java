package top.vmctcn.vmtranslationupdate.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import top.vmctcn.vmtranslationupdate.VMTranslationUpdate;
import top.vmctcn.vmtranslationupdate.util.ModConfigUtil;

@Mod(VMTranslationUpdate.MOD_ID)
public class VMTranslationUpdateClientNeoForge {
    public VMTranslationUpdateClientNeoForge() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));

        if (FMLLoader.getDist() == Dist.CLIENT) {
            onInitializeClient();
        }
    }

    public void onInitializeClient() {
        VMTranslationUpdate.init();
        ModConfigUtil.setConfigScreen();
    }
}
