package dev.mayaqq.estrogen.fabric.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.mayaqq.estrogen.client.content.EstrogenRenderer;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PostPass.class)
public class PostPassMixin {

    @WrapWithCondition(
        method = "process",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;depthFunc(I)V"
        )
    )
    private boolean depthFuncThing(int i) {
        return !EstrogenRenderer.depthInPostPass;
    }
}
