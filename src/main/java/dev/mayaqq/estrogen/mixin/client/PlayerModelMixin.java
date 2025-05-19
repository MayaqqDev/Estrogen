package dev.mayaqq.estrogen.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.BoobArmorRenderer;
import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.BoobRendering;
import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.TextureData;
import dev.mayaqq.estrogen.client.features.boobs.Boob;
import dev.mayaqq.estrogen.client.features.boobs.data.BreastArmorData;
import dev.mayaqq.estrogen.client.features.boobs.data.BreastArmorDataLoader;
import dev.mayaqq.estrogen.compat.figura.FiguraCompat;
import dev.mayaqq.estrogen.content.EstrogenEffects;
import dev.mayaqq.estrogen.injection.IPlayerModel;
import net.minecraft.Optionull;
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
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

import static dev.mayaqq.cynosure.core.PlatformHooksKt.isModLoaded;
import static dev.mayaqq.estrogen.client.features.boobs.Boob.boobFunc;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> implements IPlayerModel {
    @Unique
    private ModelPart estrogen$boobs;

    @Unique
    private ModelPart estrogen$boobJacket;

    @Unique
    private BoobArmorRenderer estrogen$boobArmor;

    @Unique
    private BoobArmorRenderer estrogen$boobArmorTrim;

    public PlayerModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void estrogen$init(ModelPart root, boolean thinArms, CallbackInfo ci) {
        if (root.hasChild("boobs")) estrogen$boobs = root.getChild("boobs");
        else Estrogen.INSTANCE.warn("An error occurred while trying to get the Estrogen Chest.");
        if (root.hasChild("boobs_jacket")) estrogen$boobJacket = root.getChild("boobs_jacket");
        else Estrogen.INSTANCE.warn("An error occurred while trying to get the Estrogen Chest Jacket.");
        estrogen$boobArmor = new BoobArmorRenderer();
        estrogen$boobArmorTrim = new BoobArmorRenderer();
    }

    @ModifyReturnValue(
            method = "createMesh(Lnet/minecraft/client/model/geom/builders/CubeDeformation;Z)Lnet/minecraft/client/model/geom/builders/MeshDefinition;",
            at = @At("RETURN")
    )
    private static MeshDefinition estrogen$getTextureModelData(MeshDefinition original, @Local PartDefinition root) {
        root.addOrReplaceChild("boobs", CubeListBuilder.create().addBox("boobs", -4.0F, 0F, 0F, 8, 2, 2, CubeDeformation.NONE, 18, 22), PartPose.ZERO);
        root.addOrReplaceChild("boobs_jacket", CubeListBuilder.create().addBox("boobs_jacket", -4.0F, 0F, 0F, 8, 2, 2, new CubeDeformation(0.25f), 18, 38), PartPose.ZERO);
        return original;
    }

    @Override
    public void estrogen$renderBoobs(PoseStack matrices, VertexConsumer vertices, int light, int overlay, AbstractClientPlayer player, float size, float yOffset) {
        if (isModLoaded("figura") && !FiguraCompat.renderBoobs(player)) return;
        if (this.estrogen$boobs == null) return;
        // Calling this here as well to check if the armor texture is empty in these specific circumstances... GOD I LOVE EDGECASES
        if (Boob.fuckedUpArmorConfigCheck(player)) {
            if (estrogen$getArmorTexture(player, false).isEmpty()) return;
        }

        this.estrogen$boobs.copyFrom(this.body);
        this.estrogen$boobs.xRot = this.body.xRot + 1.0F;
        float amplifier = Optionull.mapOrDefault(player.getEffect(EstrogenEffects.getEstrogen()), MobEffectInstance::getAmplifier, 2);
        Quaternionf bodyRotation = (new Quaternionf()).rotationZYX(this.body.zRot, this.body.yRot, this.body.xRot);
        this.estrogen$boobs.offsetPos(new Vector3f(0.0F, 4.0F + size * 0.864F * boobFunc(1 + amplifier) + yOffset, -1.9F + size * -1.944F * boobFunc(1 + amplifier)).rotate(bodyRotation));
        this.estrogen$boobs.yScale = (1 + size * 2.0F * boobFunc(1 + amplifier)) / 2.0F;
        this.estrogen$boobs.zScale = (1 + size * 2.5F * boobFunc(1 + amplifier)) / 2.0F;
        this.estrogen$boobs.render(matrices, vertices, light, overlay);

        if (this.estrogen$boobJacket == null) return;
        this.estrogen$boobJacket.visible = player.isModelPartShown(PlayerModelPart.JACKET);
        this.estrogen$boobJacket.copyFrom(this.body);
        this.estrogen$boobJacket.xRot = this.estrogen$boobs.xRot;
        this.estrogen$boobJacket.offsetPos(new Vector3f(0.0F, 4.0F + size * 0.864F * boobFunc(1 + amplifier) + yOffset, -1.9F + size * -1.944F * boobFunc(1 + amplifier)).rotate(bodyRotation));
        this.estrogen$boobJacket.yScale = this.estrogen$boobs.yScale;
        this.estrogen$boobJacket.zScale = this.estrogen$boobs.zScale;
        this.estrogen$boobJacket.render(matrices, vertices, light, overlay);
    }

    @Override
    public void estrogen$renderBoobArmor(PoseStack matrices, MultiBufferSource vertexConsumers, int light, boolean glint, float red, float green, float blue, boolean overlay, AbstractClientPlayer player, float size, float yOffset) {
        // Figura Check
        if (isModLoaded("figura") && !FiguraCompat.renderBoobArmor(player)) return;
        // Null Check
        if (this.estrogen$boobArmor == null) return;
        // Armor Data Check
        Optional<TextureData> opt = this.estrogen$getArmorTexture(player, overlay);
        if (opt.isEmpty()) {
            return;
        }
        // Rendering
        TextureData textureData = opt.get();
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(textureData.getLocation()), false, glint);
        this.estrogen$boobArmor.copyTransform(this.body);
        this.estrogen$boobArmor.setPitch(this.body.xRot);
        float amplifier = Optionull.mapOrDefault(player.getEffect(EstrogenEffects.getEstrogen()), MobEffectInstance::getAmplifier, 2);
        Quaternionf bodyRotation = (new Quaternionf()).rotationZYX(this.body.zRot, this.body.yRot, this.body.xRot);
        this.estrogen$boobArmor.translate((new Vector3f(0.0F, 4.0F + size * 0.864F * boobFunc(1 + amplifier) + yOffset, -4.0F + size * (-1.944F - 0.24F*3.0F) * boobFunc(1 + amplifier))).rotate(bodyRotation));
        this.estrogen$boobArmor.setScaleY((1 + size * 2.0F * boobFunc(1 + amplifier)) / 2.0F);
        this.estrogen$boobArmor.setScaleZ((1 + size * 2.5F * boobFunc(1 + amplifier)) / 2.0F);
        this.estrogen$boobArmor.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F, textureData.getU(), textureData.getV(), textureData.getLeftU(), textureData.getLeftV(), textureData.getRightU(), textureData.getRightV(), textureData.getTextureWidth(), textureData.getTextureHeight());
    }

    @Override
    public void estrogen$renderBoobArmorTrim(PoseStack matrices, MultiBufferSource vertexConsumers, int light, boolean bl, ArmorTrim armorTrim, ArmorMaterial armorMaterial, TextureAtlas armorTrimAtlas, AbstractClientPlayer player) {
        if (isModLoaded("figura") && !FiguraCompat.renderBoobArmor(player)) return;
        if (this.estrogen$boobArmorTrim == null) return;

        TextureAtlasSprite textureAtlasSprite = armorTrimAtlas.getSprite(bl ? armorTrim.innerTexture(armorMaterial) : armorTrim.outerTexture(armorMaterial));
        VertexConsumer vertexConsumer = textureAtlasSprite.wrap(vertexConsumers.getBuffer(Sheets.armorTrimsSheet()));
        this.estrogen$boobArmorTrim.copyTransform(this.estrogen$boobArmor);
        this.estrogen$boobArmorTrim.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, 20, 23, 18, 23, 28, 23, 64.0F, 32.0F);
    }

    @Unique
    private Optional<TextureData> estrogen$getArmorTexture(AbstractClientPlayer player, boolean overlay) {
        String string;
        ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!(itemStack.getItem() instanceof ArmorItem item)) return Optional.empty();
        BreastArmorData data;
        if ((data = BreastArmorDataLoader.INSTANCE.getData(BuiltInRegistries.ITEM.getKey(item))) != null) {
            if (overlay) {
                return data.getOverlayLocation() != null ?
                        Optional.of(new TextureData(data.getOverlayLocation(), data.getUv().getFirst(), data.getUv().getSecond(), data.getLeftUV().getFirst(), data.getLeftUV().getSecond(), data.getRightUV().getFirst(), data.getRightUV().getSecond(), data.getTextureSize().getFirst(), data.getTextureSize().getSecond())) :
                        Optional.empty();
            } else {
                return Optional.of(new TextureData(data.getTextureLocation(), data.getUv().getFirst(), data.getUv().getSecond(), data.getLeftUV().getFirst(), data.getLeftUV().getSecond(), data.getRightUV().getFirst(), data.getRightUV().getSecond(), data.getTextureSize().getFirst(), data.getTextureSize().getSecond()));
            }
        } else {
            String texture = item.getMaterial().getName();
            String domain = "minecraft";
            int idx = texture.indexOf(':');
            if (idx != -1) {
                domain = texture.substring(0, idx);
                texture = texture.substring(idx + 1);
            }
            string = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_1%s.png", domain, texture, overlay ? "_overlay" : "");
            ResourceLocation location;
            return (location = BoobRendering.getARMOR_TEXTURE_CACHE().computeIfAbsent(string, ResourceLocation::tryParse)) != null ?
                    Optional.of(new TextureData(location, 20f, 23f, 18f, 23f, 28f, 23f, 64.0F, 32.0F)) :
                    Optional.empty();
        }
    }

    @Inject(method = "setAllVisible", at = @At("RETURN"))
    private void estrogen$setVisible(boolean visible, CallbackInfo ci) {
        if (this.estrogen$boobs != null)this.estrogen$boobs.visible = visible;
        if (this.estrogen$boobJacket != null) this.estrogen$boobJacket.visible = visible;
        if (this.estrogen$boobArmor != null) this.estrogen$boobArmor.setVisible(visible);
    }
}