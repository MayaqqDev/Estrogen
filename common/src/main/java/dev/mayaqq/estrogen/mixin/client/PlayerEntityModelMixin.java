package dev.mayaqq.estrogen.mixin.client;

import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.equipment.armor.BaseArmorItem;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.BoobArmorRenderer;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.PlayerEntityModelExtension;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
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

@Mixin(PlayerModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity> extends HumanoidModel<T> implements PlayerEntityModelExtension {

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    @Unique
    private ModelPart boobs;

    @Unique
    private BoobArmorRenderer boobArmor;

    public PlayerEntityModelMixin(ModelPart root) {
        super(root);
    }

    @ModifyReturnValue(
            method = "createMesh(Lnet/minecraft/client/model/geom/builders/CubeDeformation;Z)Lnet/minecraft/client/model/geom/builders/MeshDefinition;",
            at = @At("RETURN")
    )
    private static MeshDefinition estrogen$getTextureModelData(MeshDefinition original) {
        PartDefinition modelPartData = original.getRoot();
        modelPartData.addOrReplaceChild("boobs", CubeListBuilder.create().addBox("boobs", -4.0F, 0F, 0F, 8, 2, 2, CubeDeformation.NONE, 18, 22), PartPose.ZERO);
        return original;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void estrogen$init(ModelPart root, boolean thinArms, CallbackInfo ci) {
        boobs = root.getChild("boobs");
        boobArmor = new BoobArmorRenderer(Collections.singletonList(new BoobArmorRenderer.BoobArmorModel(18, 23, 8, 2, 2, false, 64.0F, 32.0F)));
    }

    @Override
    public void estrogen$renderBoobs(PoseStack matrices, VertexConsumer vertices, int light, int overlay, AbstractClientPlayer player, float size) {
        this.boobs.copyFrom(this.body);
        this.boobs.xRot = this.body.xRot + 1.0F;
        float amplifier = player.getEffect(EstrogenEffects.ESTROGEN_EFFECT).getAmplifier() / 10.0F;
        Quaternionf bodyRotation = (new Quaternionf()).rotationZYX(this.body.zRot, this.body.yRot, this.body.xRot);
        this.boobs.offsetPos(new Vector3f(0.0F, 4.0F + size * 0.864F * (1 + amplifier), -1.9F + size * -1.944F * (1 + amplifier)).rotate(bodyRotation));
        this.boobs.yScale = (1 + size * 2.0F * (1 + amplifier)) / 2.0F;
        this.boobs.zScale = (1 + size * 2.5F * (1 + amplifier)) / 2.0F;
        this.boobs.render(matrices, vertices, light, overlay);
    }

    @Override
    public void estrogen$renderBoobArmor(PoseStack matrices, MultiBufferSource vertexConsumers, int light, boolean glint, float red, float green, float blue, @Nullable String overlay, AbstractClientPlayer player, float size) {
        ResourceLocation texture = this.getArmorTexture(player, overlay);
        if (texture == null) {
            return;
        }
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(texture), false, glint);
        this.boobArmor.copyTransform(this.body);
        this.boobArmor.pitch = this.body.xRot;
        float amplifier = player.getEffect(EstrogenEffects.ESTROGEN_EFFECT).getAmplifier() / 10.0F;
        Quaternionf bodyRotation = (new Quaternionf()).rotationZYX(this.body.zRot, this.body.yRot, this.body.xRot);
        this.boobArmor.translate((new Vector3f(0.0F, 4.0F + size * 0.864F * (1 + amplifier), -3.9F + size * -1.944F * (1 + amplifier))).rotate(bodyRotation));
        this.boobArmor.scaleY = (1 + size * 2.0F * (1 + amplifier)) / 2.0F;
        this.boobArmor.scaleZ = (1 + size * 2.5F * (1 + amplifier)) / 2.0F;
        this.boobArmor.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

    private ResourceLocation getArmorTexture(AbstractClientPlayer player, @Nullable String overlay) {
        String string;
        ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        ArmorItem item = (ArmorItem) itemStack.getItem();
        if (item instanceof BaseArmorItem) {
            string = ((BaseArmorItem) item).getArmorTexture(itemStack, player, EquipmentSlot.CHEST, overlay);
        } else {
            String texture = item.getMaterial().getName();
            String domain = "minecraft";
            int idx = texture.indexOf(':');
            if (idx != -1) {
                domain = texture.substring(0, idx);
                texture = texture.substring(idx + 1);
            }
            string = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_1%s.png", domain, texture, overlay == null ? "" : String.format(java.util.Locale.ROOT, "_%s", overlay));
        }
        return ARMOR_TEXTURE_CACHE.computeIfAbsent(string, ResourceLocation::tryParse);
    }

    @Inject(method = "setAllVisible", at = @At("RETURN"))
    private void estrogen$setVisible(boolean visible, CallbackInfo ci) {
        this.boobs.visible = visible;
        this.boobArmor.visible = visible;
    }
}
