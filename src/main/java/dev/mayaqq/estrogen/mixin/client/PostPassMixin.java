package dev.mayaqq.estrogen.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostPass.class)
public class PostPassMixin {

    @Shadow @Final private EffectInstance effect;

    @Inject(
        method = "process",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/EffectInstance;apply()V"
        )
    )
    private void additionalUniforms(float partialTicks, CallbackInfo ci) {
        effect.safeGetUniform("EstrogenFarPlane").set(Minecraft.getInstance().gameRenderer.getDepthFar());
    }
}
