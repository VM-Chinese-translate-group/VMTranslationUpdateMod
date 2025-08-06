package top.vmctcn.vmtu.mod.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.vmctcn.vmtu.mod.VMTranslationUpdate;

@Mixin(LanguageOptionsScreen.class)
public abstract class LanguageScreenMixin extends GameOptionsScreen {
    public LanguageScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Inject(method = "onDone",at = @At("HEAD"))
    public void notReloadResourcePacks(CallbackInfo ci){
        VMTranslationUpdate.LANG_RELOAD = true;
    }
}