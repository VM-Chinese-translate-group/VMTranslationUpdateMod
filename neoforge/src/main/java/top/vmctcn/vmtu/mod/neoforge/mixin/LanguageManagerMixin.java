package top.vmctcn.vmtu.mod.neoforge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;

import java.util.List;
import java.util.Map;

@Mixin(LanguageManager.class)
public class LanguageManagerMixin {
    @Shadow
    private String currentCode;

    @Inject(method = "onResourceManagerReload", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z", shift = At.Shift.AFTER))
    private void insertQueue(ResourceManager resourceManager, CallbackInfo ci, @Local(name = /*? if >= 26.1 {*/"languageStack"/*?} else {*//*"list"*//*?}*/) List<String> languageStack) {
        Map<String, String[]> queues = LanguageUtils.getFallbackQueues();
        if (!queues.containsKey(currentCode)) return;

        languageStack.addAll(List.of(queues.get(currentCode)));
    }

    @Inject(method = "onResourceManagerReload", at = @At("TAIL"))
    private void logLoadedQueue(ResourceManager resourceManager, CallbackInfo ci, @Local(name = /*? if >= 26.1 {*/"languageStack"/*?} else {*//*"list"*//*?}*/) List<String> languageStack) {
        ModContexts.LOGGER.info("Language Loading Order: {}(In reverse order)", languageStack);
    }
}
