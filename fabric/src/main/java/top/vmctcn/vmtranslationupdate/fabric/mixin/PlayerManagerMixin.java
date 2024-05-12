package top.vmctcn.vmtranslationupdate.fabric.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.vmctcn.vmtranslationupdate.ModEvents;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("RETURN"))
    private void placeNewPlayer(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        ModEvents.playerJoinEvent(player);
    }
}