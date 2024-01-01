package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.entity.player.features.boobs.BoobFeatureRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void estrogen$init(CallbackInfo ci) {
        PlayerRenderer playerRenderer = (PlayerRenderer) (Object) this;
        playerRenderer.addLayer(new BoobFeatureRenderer(playerRenderer));
    }
}
