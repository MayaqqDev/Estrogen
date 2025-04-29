package dev.mayaqq.estrogen.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.mayaqq.estrogen.content.EstrogenEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
public class FontStringRenderOutputMixin {
    @WrapOperation(
            method = "accept",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Style;isObfuscated()Z"
            )
    )
    private boolean modifyObfuscated(Style instance, Operation<Boolean> original) {
        //TODO: Optimize this, probably have like a common dreaming class where we can check if is dreaming or not.
        if (Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().player.hasEffect(EstrogenEffects.INSTANCE.getDREAMING())) {
                return true;
            }
        }
        return original.call(instance);
    }
}
