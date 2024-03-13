package dev.mayaqq.estrogen.mixin.client;

import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.simibubi.create.content.equipment.armor.BaseArmorItem;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.BoobArmorRenderer;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.PlayerEntityModelExtension;
import dev.mayaqq.estrogen.integrations.figura.FiguraCompat;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.client.Minecraft;
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
        Mod figura = Platform.getOptionalMod("figura").orElse(null);
        if (figura != null && figura.getVersion().split("\\.")[2].startsWith("3")) {
            if (!FiguraCompat.renderBoobs(Minecraft.getInstance().player)) return;
        }
        float amplifier = player.getEffect(EstrogenEffects.ESTROGEN_EFFECT).getAmplifier() / 10.0F;
        Quaternion bodyRotation = Vector3f.XP.rotation(this.body.xRot);
        Vector3f transform = new Vector3f(0.0F, 4.0F+size*0.864F*(1+amplifier), -1.9F+size*-1.944F*(1+amplifier));
        transform.transform(bodyRotation);
        transform.add(this.body.x, this.body.y, this.body.z);
        float yScale = (1 + size*2.0F*(1+amplifier)) / 2.0F;
        float zScale = (1 + size*2.5F*(1+amplifier)) / 2.0F;
        matrices.pushPose();
        matrices.translate(transform.x() / 16.0F, transform.y() / 16.0F, transform.z() / 16.0F);
        matrices.mulPose(Vector3f.XP.rotation(this.body.xRot + 1.0F));
        matrices.scale(1.0F, yScale, zScale);

        this.boobs.render(matrices, vertices, light, overlay);
        matrices.popPose();
    }

    @Override
    public void estrogen$renderBoobArmor(PoseStack matrices, MultiBufferSource vertexConsumers, int light, boolean glint, float red, float green, float blue, @Nullable String overlay, AbstractClientPlayer player, float size) {
        Mod figura = Platform.getOptionalMod("figura").orElse(null);
        if (figura != null && figura.getVersion().split("\\.")[2].startsWith("3")) {
            if (!FiguraCompat.renderBoobArmor(Minecraft.getInstance().player)) return;
        }
        ResourceLocation texture = this.getArmorTexture(player, overlay);
        if (texture == null) {
            return;
        }

        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(texture), false, glint);
        this.boobArmor.copyTransform(this.body);
        this.boobArmor.pitch = this.body.xRot;
        float amplifier = player.getEffect(EstrogenEffects.ESTROGEN_EFFECT).getAmplifier() / 10.0F;
        Quaternion bodyRotation = Vector3f.XP.rotation(this.body.xRot);
        Vector3f translation = new Vector3f(0.0F, 4.0F + size * 0.864F * (1 + amplifier), -3.9F + size * -1.944F * (1 + amplifier));
        translation.transform(bodyRotation);
        this.boobArmor.translate(translation);
        this.boobArmor.scaleY = (1 + size * 2.0F * (1 + amplifier)) / 2.0F;
        this.boobArmor.scaleZ = (1 + size * 2.5F * (1 + amplifier)) / 2.0F;
        this.boobArmor.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

    @Unique
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
