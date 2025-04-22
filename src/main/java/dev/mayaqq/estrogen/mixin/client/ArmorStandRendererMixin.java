package dev.mayaqq.estrogen.mixin.client;

import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandRenderer.class)
public class ArmorStandRendererMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void estrogen$modelLayers$init(EntityRendererProvider.Context context, CallbackInfo ci) {
        ArmorStandRenderer armorStandRenderer = (ArmorStandRenderer) (Object) this;
        //TODO: armorStandRenderer.addLayer(new MothElytraLayer<>(armorStandRenderer, context.getModelSet()));
    }
}
