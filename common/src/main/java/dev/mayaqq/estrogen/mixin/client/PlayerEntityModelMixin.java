package dev.mayaqq.estrogen.mixin.client;

import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.equipment.armor.BaseArmorItem;
import com.teamresourceful.resourcefullib.common.utils.modinfo.ModInfoUtils;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.BoobArmorRenderer;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.PlayerEntityModelExtension;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.TextureData;
import dev.mayaqq.estrogen.integrations.figura.FiguraCompat;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.resources.BreastArmorData;
import dev.mayaqq.estrogen.resources.BreastArmorDataLoader;
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

import java.util.Map;
import java.util.Optional;

@Mixin(PlayerModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity> extends HumanoidModel<T> implements PlayerEntityModelExtension {

    @Unique
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_CACHE = Maps.newHashMap();

    @Unique
    private ModelPart estrogen$boobs;

    @Unique
    private ModelPart estrogen$boobJacket;

    @Unique
    private BoobArmorRenderer estrogen$boobArmor;

    @Unique
    private BoobArmorRenderer estrogen$boobArmorTrim;

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
        modelPartData.addOrReplaceChild("boobs_jacket", CubeListBuilder.create().addBox("boobs_jacket", -4.0F, 0F, 0F, 8, 2, 2, new CubeDeformation(0.25f), 18, 38), PartPose.ZERO);
        return original;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void estrogen$init(ModelPart root, boolean thinArms, CallbackInfo ci) {
        estrogen$boobs = root.getChild("boobs");
        estrogen$boobJacket = root.getChild("boobs_jacket");
        estrogen$boobArmor = new BoobArmorRenderer();
        estrogen$boobArmorTrim = new BoobArmorRenderer();
    }

    @Override
    public void estrogen$renderBoobs(PoseStack matrices, VertexConsumer vertices, int light, int overlay, AbstractClientPlayer player, float size, float yOffset) {
        if (ModInfoUtils.isModLoaded("figura") && !FiguraCompat.renderBoobs(player)) return;

        this.estrogen$boobs.copyFrom(this.body);
        this.estrogen$boobs.xRot = this.body.xRot + 1.0F;
        float amplifier = player.getEffect(EstrogenEffects.ESTROGEN_EFFECT.get()).getAmplifier() / 10.0F;
        Quaternionf bodyRotation = (new Quaternionf()).rotationZYX(this.body.zRot, this.body.yRot, this.body.xRot);
        this.estrogen$boobs.offsetPos(new Vector3f(0.0F, 4.0F + size * 0.864F * (1 + amplifier) + yOffset, -1.9F + size * -1.944F * (1 + amplifier)).rotate(bodyRotation));
        this.estrogen$boobs.yScale = (1 + size * 2.0F * (1 + amplifier)) / 2.0F;
        this.estrogen$boobs.zScale = (1 + size * 2.5F * (1 + amplifier)) / 2.0F;
        this.estrogen$boobs.render(matrices, vertices, light, overlay);

        this.estrogen$boobJacket.visible = player.isModelPartShown(PlayerModelPart.JACKET);
        this.estrogen$boobJacket.copyFrom(this.body);
        this.estrogen$boobJacket.xRot = this.estrogen$boobs.xRot;
        this.estrogen$boobJacket.offsetPos(new Vector3f(0.0F, 4.0F + size * 0.864F * (1 + amplifier) + yOffset, -1.9F + size * -1.944F * (1 + amplifier)).rotate(bodyRotation));
        this.estrogen$boobJacket.yScale = this.estrogen$boobs.yScale;
        this.estrogen$boobJacket.zScale = this.estrogen$boobs.zScale;
        this.estrogen$boobJacket.render(matrices, vertices, light, overlay);

    }

    @Override
    public void estrogen$renderBoobArmor(PoseStack matrices, MultiBufferSource vertexConsumers, int light, boolean glint, float red, float green, float blue, boolean overlay, AbstractClientPlayer player, float size, float yOffset) {
        if (ModInfoUtils.isModLoaded("figura") && !FiguraCompat.renderBoobArmor(player)) return;

        Optional<TextureData> opt = this.estrogen$getArmorTexture(player, overlay);
        if (opt.isEmpty()) {
            return;
        }
        TextureData textureData = opt.get();
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(textureData.location()), false, glint);
        this.estrogen$boobArmor.copyTransform(this.body);
        this.estrogen$boobArmor.pitch = this.body.xRot;
        float amplifier = player.getEffect(EstrogenEffects.ESTROGEN_EFFECT.get()).getAmplifier() / 10.0F;
        Quaternionf bodyRotation = (new Quaternionf()).rotationZYX(this.body.zRot, this.body.yRot, this.body.xRot);
        this.estrogen$boobArmor.translate((new Vector3f(0.0F, 4.0F + size * 0.864F * (1 + amplifier) + yOffset, -4.0F + size * (-1.944F - 0.24F*3.0F) * (1 + amplifier))).rotate(bodyRotation));
        this.estrogen$boobArmor.scaleY = (1 + size * 2.0F * (1 + amplifier)) / 2.0F;
        this.estrogen$boobArmor.scaleZ = (1 + size * 2.5F * (1 + amplifier)) / 2.0F;
        this.estrogen$boobArmor.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F, textureData.u(), textureData.v(), textureData.leftU(), textureData.leftV(), textureData.rightU(), textureData.rightV(), textureData.textureWidth(), textureData.textureHeight());
    }

    @Override
    public void estrogen$renderBoobArmorTrim(PoseStack matrices, MultiBufferSource vertexConsumers, int light, boolean bl, ArmorTrim armorTrim, ArmorMaterial armorMaterial, TextureAtlas armorTrimAtlas, AbstractClientPlayer player) {
        if (ModInfoUtils.isModLoaded("figura") && !FiguraCompat.renderBoobArmor(player)) return;

        TextureAtlasSprite textureAtlasSprite = armorTrimAtlas.getSprite(bl ? armorTrim.innerTexture(armorMaterial) : armorTrim.outerTexture(armorMaterial));
        VertexConsumer vertexConsumer = textureAtlasSprite.wrap(vertexConsumers.getBuffer(Sheets.armorTrimsSheet()));
        this.estrogen$boobArmorTrim.render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, 20, 23, 18, 23, 28, 23, 64.0F, 32.0F);
    }

    @Unique
    private Optional<TextureData> estrogen$getArmorTexture(AbstractClientPlayer player, boolean overlay) {
        String string;
        ItemStack itemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        ArmorItem item = (ArmorItem) itemStack.getItem();
        BreastArmorData data;
        if ((data = BreastArmorDataLoader.INSTANCE.getData(BuiltInRegistries.ITEM.getKey(item))) != null) {
            if (overlay) {
                return data.overlayLocation != null ?
                        Optional.of(new TextureData(data.overlayLocation, data.uv.getFirst(), data.uv.getSecond(), data.leftUV.getFirst(), data.leftUV.getSecond(), data.rightUV.getFirst(), data.rightUV.getSecond(), data.textureSize.getFirst(), data.textureSize.getSecond())) :
                        Optional.empty();
            } else {
                return Optional.of(new TextureData(data.textureLocation, data.uv.getFirst(), data.uv.getSecond(), data.leftUV.getFirst(), data.leftUV.getSecond(), data.rightUV.getFirst(), data.rightUV.getSecond(), data.textureSize.getFirst(), data.textureSize.getSecond()));
            }
        } else {
            if (item instanceof BaseArmorItem) {
                string = ((BaseArmorItem) item).getArmorTexture(itemStack, player, EquipmentSlot.CHEST, "overlay");
            } else {
                String texture = item.getMaterial().getName();
                String domain = "minecraft";
                int idx = texture.indexOf(':');
                if (idx != -1) {
                    domain = texture.substring(0, idx);
                    texture = texture.substring(idx + 1);
                }
                string = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_1%s.png", domain, texture, overlay ? "_overlay" : "");
            }
            ResourceLocation location;
            return (location = ARMOR_TEXTURE_CACHE.computeIfAbsent(string, ResourceLocation::tryParse)) != null ?
                    Optional.of(new TextureData(location, 20, 23, 18, 23, 28, 23, 64.0F, 32.0F)) :
                    Optional.empty();
        }
    }

    @Inject(method = "setAllVisible", at = @At("RETURN"))
    private void estrogen$setVisible(boolean visible, CallbackInfo ci) {
        this.estrogen$boobs.visible = visible;
        this.estrogen$boobJacket.visible = visible;
        this.estrogen$boobArmor.visible = visible;
    }
}
