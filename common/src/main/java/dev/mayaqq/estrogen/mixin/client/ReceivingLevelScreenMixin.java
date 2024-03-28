package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.c2s.FinishLoadingPacket;
import dev.mayaqq.estrogen.networking.messages.c2s.SetChestConfigPacket;
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
        EstrogenNetworkManager.CHANNEL.sendToServer(new SetChestConfigPacket(EstrogenConfig.client().chestFeature.get(), EstrogenConfig.client().chestArmor.get(), EstrogenConfig.client().chestPhysics.get(), EstrogenConfig.client().chestBounciness.get().floatValue(), EstrogenConfig.client().chestDamping.get().floatValue()));
    }
}
