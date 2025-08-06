package top.vmctcn.vmtu.mod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(LanguageManager.class)
public abstract class LanguageManagerMixin {
    @Shadow private String currentLanguageCode;

    private static final Map<String, List<String>> QUEUES = Map.of(
            "zh_cn", List.of("zh_hk", "zh_tw"),
            "zh_hk", List.of("zh_cn", "zh_tw"),
            "zh_tw", List.of("zh_cn", "zh_hk"),
            "lzh",   List.of("zh_cn", "zh_hk", "zh_tw")
    );

    @Inject(method = "reload", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z", shift = At.Shift.AFTER))
    private void injectQueue(ResourceManager manager, CallbackInfo ci, @Local List<String> list) {
        var fallback = QUEUES.get(currentLanguageCode);
        if (fallback != null) list.addAll(fallback);
    }
}
