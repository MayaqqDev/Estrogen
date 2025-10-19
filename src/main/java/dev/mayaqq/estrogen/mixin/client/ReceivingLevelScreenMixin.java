package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.EstrogenClientKt;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReceivingLevelScreen.class)
public class ReceivingLevelScreenMixin {
    @Inject(method = "onClose()V", at = @At("TAIL"))
    private void estrogen$onClose(CallbackInfo ci) {
        EstrogenClientKt.setChestConfigSet(false);
    }
}
