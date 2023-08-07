package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.entity.player.features.boob.PlayerEntityModelExtension;
import dev.mayaqq.estrogen.registry.EffectRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T> implements PlayerEntityModelExtension {
    @Shadow @Final public ModelPart jacket;
    @Unique
    private ModelPart boobs;

    public PlayerEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void estrogen$init(ModelPart root, boolean thinArms, CallbackInfo ci) {
        boobs = root.getChild("boobs");
    }

    @ModifyReturnValue(
            method = "getTexturedModelData(Lnet/minecraft/client/model/Dilation;Z)Lnet/minecraft/client/model/ModelData;",
            at = @At("RETURN")
    )
    private static ModelData estrogen$getTextureModelData(ModelData modelData) {
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("boobs", ModelPartBuilder.create().cuboid("boobs", -4.0F, -1.0F, -5.0F, 8, 4, 4, Dilation.NONE, 16, 20), ModelTransform.NONE);
        return modelData;
    }

    @Override
    public void estrogen$renderBoobs(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, AbstractClientPlayerEntity player) {
        this.boobs.copyTransform(this.body);
        this.boobs.pitch = this.body.pitch + 1.0F;
        Estrogen.LOGGER.info("boobs zScale: " + this.boobs.zScale);
        this.boobs.zScale = 1 + player.getStatusEffect(EffectRegistry.WOMAN_EFFECT).getAmplifier() / 10.0F;
        this.boobs.render(matrices, vertices, light, overlay);
    }

    @Inject(method = "setVisible", at = @At("RETURN"))
    private void estrogen$setVisible(boolean visible, CallbackInfo ci) {
        this.boobs.visible = visible;
    }
}
