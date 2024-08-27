package dev.mayaqq.estrogen.mixin.client;

import com.jozufozu.flywheel.core.model.ShadeSeparatingVertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShadeSeparatingVertexConsumer.class)
public abstract class DevsWhatWereYouSmoking {

    @Shadow protected abstract void setActiveConsumer(boolean shaded);

    @Inject(
        method = "prepare",
        at = @At("RETURN")
    )
    public void preventDumbassCrash(VertexConsumer shadedConsumer, VertexConsumer unshadedConsumer, CallbackInfo ci) {
        this.setActiveConsumer(true);
    }
}
