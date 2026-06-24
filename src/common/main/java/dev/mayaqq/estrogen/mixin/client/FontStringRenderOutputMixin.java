package dev.mayaqq.estrogen.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.mayaqq.estrogen.client.features.TextRendererFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
public class FontStringRenderOutputMixin {
    @ModifyExpressionValue(
            method = "accept",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Style;isObfuscated()Z"
            )
    )
    private boolean modifyObfuscated(boolean original) {
        return original || TextRendererFeatures.getObfuscate();
    }
}
