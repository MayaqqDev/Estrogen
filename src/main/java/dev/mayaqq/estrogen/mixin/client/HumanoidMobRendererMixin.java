package dev.mayaqq.estrogen.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public class HumanoidMobRendererMixin {
    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/minecraft/client/model/HumanoidModel;FFFF)V", at = @At("RETURN"))
    private void estrogen$init(CallbackInfo ci, @Local(argsOnly = true) EntityRendererProvider.Context arg) {
        HumanoidMobRenderer humanoidMobRenderer = (HumanoidMobRenderer) (Object) this;
        //TODO: humanoidMobRenderer.addLayer(new MothElytraLayer<>(humanoidMobRenderer, arg.getModelSet()));
    }
}
