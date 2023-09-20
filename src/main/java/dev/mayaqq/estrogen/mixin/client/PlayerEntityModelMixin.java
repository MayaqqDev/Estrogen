package dev.mayaqq.estrogen.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.PlayerEntityModelExtension;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T> implements PlayerEntityModelExtension {
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
        modelPartData.addChild("boobs", ModelPartBuilder.create().cuboid("boobs", -4.0F, 0F, 0F, 8, 2, 2, Dilation.NONE, 18, 22), ModelTransform.NONE);
        return modelData;
    }

    @Override
    public void estrogen$renderBoobs(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, AbstractClientPlayerEntity player, float size) {
        this.boobs.copyTransform(this.body);
        this.boobs.pitch = this.body.pitch + 1.0F;
        float amplifier = player.getStatusEffect(EstrogenEffects.ESTROGEN_EFFECT).getAmplifier() / 10.0F;
        Vec3f transform = new Vec3f(0.0F, 4.0F+size*0.864F*(1+amplifier), -1.9F+size*-1.944F*(1+amplifier));
        this.boobs.setTransform(ModelTransform.pivot(transform.getX(), transform.getY(), transform.getZ()));
        if (player.isInSneakingPose()) {
            Vec3f transform2 = new Vec3f(0.0F, -0.4F, 2.0F);
            this.boobs.setTransform(ModelTransform.pivot(transform2.getX(), transform2.getY(), transform2.getZ()));
        }
        //this.boobs.yScale = (1 + size*2.0F*(1+amplifier)) / 2.0F;
        //this.boobs.zScale = (1 + size*2.5F*(1+amplifier)) / 2.0F;

        this.boobs.render(matrices, vertices, light, overlay);
    }

    @Inject(method = "setVisible", at = @At("RETURN"))
    private void estrogen$setVisible(boolean visible, CallbackInfo ci) {
        this.boobs.visible = visible;
    }
}
