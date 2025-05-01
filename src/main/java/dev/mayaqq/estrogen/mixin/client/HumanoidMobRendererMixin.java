package dev.mayaqq.estrogen.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra.MothElytraLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public abstract class HumanoidMobRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {

    public HumanoidMobRendererMixin(EntityRendererProvider.Context context, M m, float v) {
        super(context, m, v);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/minecraft/client/model/HumanoidModel;FFFF)V", at = @At("RETURN"))
    private void estrogen$init(CallbackInfo ci, @Local(argsOnly = true) EntityRendererProvider.Context arg) {
        HumanoidMobRenderer humanoidMobRenderer = (HumanoidMobRenderer) (Object) this;
        this.addLayer(new MothElytraLayer(humanoidMobRenderer, arg.getModelSet()));
    }
}
