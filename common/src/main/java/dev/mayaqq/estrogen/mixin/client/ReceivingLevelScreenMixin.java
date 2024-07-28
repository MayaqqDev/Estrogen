package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.config.ConfigSync;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.c2s.FinishLoadingPacket;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReceivingLevelScreen.class)
public abstract class ReceivingLevelScreenMixin {
    @Inject(method = "onClose()V", at = @At("TAIL"))
    private void estrogen$onClose(CallbackInfo ci) {
        EstrogenNetworkManager.CHANNEL.sendToServer(new FinishLoadingPacket());
        ConfigSync.sendCurrentConfig();
    }
}
