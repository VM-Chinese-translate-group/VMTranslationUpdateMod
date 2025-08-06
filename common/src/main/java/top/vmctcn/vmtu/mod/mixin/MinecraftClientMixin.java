package top.vmctcn.vmtu.mod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow public abstract ResourceManager getResourceManager();
    @Shadow public Screen currentScreen;
    @Shadow @Final private LanguageManager languageManager;

    @Inject(method = "reloadResources()Ljava/util/concurrent/CompletableFuture;", at = @At("HEAD"), cancellable = true)
    public void onReloadResources(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (VMTranslationUpdate.LANG_RELOAD) {
            languageManager.reload(getResourceManager());
            VMTranslationUpdate.LANG_RELOAD = false;
            cir.setReturnValue(null);
            cir.cancel();
        }
    }

    @Inject(method = "setOverlay", at = @At("HEAD"), cancellable = true)
    public void onSetOverlay(Overlay overlay, CallbackInfo ci) {
        if (currentScreen instanceof LanguageOptionsScreen || currentScreen instanceof CraftingScreen) {
            ci.cancel();
        }
    }
}
