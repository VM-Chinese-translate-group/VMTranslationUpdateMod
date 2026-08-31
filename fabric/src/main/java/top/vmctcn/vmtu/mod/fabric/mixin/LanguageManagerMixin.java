package top.vmctcn.vmtu.mod.fabric.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.utils.LanguageUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Mixin(LanguageManager.class)
public abstract class LanguageManagerMixin {
    @Shadow
    private String currentCode;

    @Shadow
    @Nullable
    public abstract LanguageInfo getLanguage(String string);

    @ModifyArg(
            method = "onResourceManagerReload",
            at = @At(
                    value = "INVOKE",
                    //? if >= 1.20.1 {
                    target = "Lnet/minecraft/client/resources/language/ClientLanguage;loadFrom(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Z)Lnet/minecraft/client/resources/language/ClientLanguage;"
                    //?} else {
                    /*target = "Lnet/minecraft/client/resources/language/TranslationStorage;load(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;)Lnet/minecraft/client/resources/language/TranslationStorage;"*/
                    //?}
            ),
            index = 1
    )
    private List</*? if >= 1.20.1 {*/String/*?} else {*//*LanguageInfo*//*?}*/> insertQueue(List</*? if >= 1.20.1 {*/String/*?} else {*//*LanguageInfo*//*?}*/> languageStack) {
        Map<String, String[]> queues = LanguageUtils.getFallbackQueues();
        if (!queues.containsKey(currentCode)) {
            ModContexts.LOGGER.info("Language Loading Order: {}(In reverse order)", languageStack);
            return languageStack;
        }

        //? if >= 1.20.1 {
        List<String> result = Lists.newArrayList(languageStack);
        result.addAll(Math.max(0, result.size() - 1), Arrays.asList(queues.get(currentCode)));
        //?} else {
        /*List<LanguageInfo> result = Lists.newArrayList(languageStack);
        result.addAll(Math.max(0, result.size() - 1), Arrays.stream(queues.get(currentCode))
                .map(this::getLanguage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        *///?}

        ModContexts.LOGGER.info("Language Loading Order: {}(In reverse order)", result);
        return result;
    }
}
