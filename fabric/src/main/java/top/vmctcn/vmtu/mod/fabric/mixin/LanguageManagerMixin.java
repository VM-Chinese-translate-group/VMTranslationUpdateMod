package top.vmctcn.vmtu.mod.fabric.mixin;

import com.google.common.collect.Lists;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(LanguageManager.class)
public abstract class LanguageManagerMixin {
    @Shadow
    private String currentCode;

    @Shadow
    @Nullable
    public abstract LanguageInfo getLanguage(String string);

    @Inject(
            method = "onResourceManagerReload",
            at = @At(
                    value = "INVOKE",
                    /*? if >= 1.20.1 {*/
                    target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z",
                    /*?} else {*/
                    /*target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    *//*?}*/
                    shift = At.Shift.AFTER
            )
    )
    private void insertQueue(
            ResourceManager resourceManager,
            CallbackInfo ci,
            @Local(name = /*? if >= 26.1 {*/"languageStack"/*?} else {*//*"list"*//*?}*/) List</*? if >= 1.20.1 {*/String/*?} else {*//*LanguageInfo*//*?}*/> languageStack
    ) {
        Map<String, String[]> queues = LanguageUtils.getFallbackQueues();
        if (!queues.containsKey(currentCode)) return;

        //? if >= 1.20.1 {
        languageStack.addAll(List.of(queues.get(currentCode)));
        //?} else {
        /*List<LanguageInfo> listLanguageInfo = Arrays.stream(queues.get(currentCode))
                .map(this::getLanguage)
                .collect(Collectors.toCollection(Lists::newArrayList));

        languageStack.addAll(listLanguageInfo);
        *///?}
    }

    @Inject(method = "onResourceManagerReload", at = @At("TAIL"))
    private void logLoadedQueue(
            ResourceManager resourceManager,
            CallbackInfo ci,
            @Local(name = /*? if >= 26.1 {*/"languageStack"/*?} else {*//*"list"*//*?}*/) List</*? if >= 1.20.1 {*/String/*?} else {*//*LanguageInfo*//*?}*/> languageStack
    ) {
        ModContexts.LOGGER.info("Language Loading Order: {}(In reverse order)", languageStack);
    }
}
