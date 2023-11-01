package dev.mayaqq.estrogen.mixin.client;

import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.BoobArmorRenderer;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.PlayerEntityModelExtension;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Map;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T> implements PlayerEntityModelExtension {

    private static final Map<String, Identifier> ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    @Unique
    private ModelPart boobs;

    @Unique
    private BoobArmorRenderer boobArmor;

    public PlayerEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void estrogen$init(ModelPart root, boolean thinArms, CallbackInfo ci) {
        boobs = root.getChild("boobs");
        boobArmor = new BoobArmorRenderer(Collections.singletonList(new BoobArmorRenderer.BoobArmorModel(18, 23, 8, 2, 2, false, 64.0F, 32.0F)));
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
        Quaternionf bodyRotation = (new Quaternionf()).rotationZYX(this.body.roll, this.body.yaw, this.body.pitch);
        this.boobs.translate((new Vector3f(0.0F, 4.0F+size*0.864F*(1+amplifier), -1.9F+size*-1.944F*(1+amplifier))).rotate(bodyRotation));
        this.boobs.scaleY = (1 + size*2.0F*(1+amplifier)) / 2.0F;
        this.boobs.scaleZ = (1 + size*2.5F*(1+amplifier)) / 2.0F;
        this.boobs.render(matrices, vertices, light, overlay);
    }

    @Override
    public void estrogen$renderBoobArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorItem item, boolean glint, boolean secondTextureLayer, float red, float green, float blue, @Nullable String overlay, AbstractClientPlayerEntity player, float size) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(item, secondTextureLayer, overlay)), false, glint);
        this.boobArmor.copyTransform(this.body);
        this.boobArmor.pitch = this.body.pitch;
        float amplifier = player.getStatusEffect(EstrogenEffects.ESTROGEN_EFFECT).getAmplifier() / 10.0F;
        Quaternionf bodyRotation = (new Quaternionf()).rotationZYX(this.body.roll, this.body.yaw, this.body.pitch);
        this.boobArmor.translate((new Vector3f(0.0F, 4.0F+size*0.864F*(1+amplifier), -3.9F+size*-1.944F*(1+amplifier))).rotate(bodyRotation));
        this.boobArmor.scaleY = (1 + size*2.0F*(1+amplifier)) / 2.0F;
        this.boobArmor.scaleZ = (1 + size*2.5F*(1+amplifier)) / 2.0F;
        this.boobArmor.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
    }

    private Identifier getArmorTexture(ArmorItem item, boolean legs, @Nullable String overlay) {
        String var10000 = item.getMaterial().getName();
        String string = "textures/models/armor/" + var10000 + "_layer_" + (legs ? 2 : 1) + (overlay == null ? "" : "_" + overlay) + ".png";
        return (Identifier)ARMOR_TEXTURE_CACHE.computeIfAbsent(string, Identifier::new);
    }

    @Inject(method = "setVisible", at = @At("RETURN"))
    private void estrogen$setVisible(boolean visible, CallbackInfo ci) {
        this.boobs.visible = visible;
    }
}
