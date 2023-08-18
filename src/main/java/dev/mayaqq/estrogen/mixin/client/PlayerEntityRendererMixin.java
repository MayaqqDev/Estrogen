package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.entity.player.features.boobs.BoobFeatureRenderer;
import dev.mayaqq.estrogen.utils.extensions.LivingEntityRendererExtension;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void estrogen$PlayerEntityRenderer(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        ((LivingEntityRendererExtension) (PlayerEntityRenderer) (Object) this).estrogen$addFeature(new BoobFeatureRenderer(((PlayerEntityRenderer) (Object) this)));
    }
}