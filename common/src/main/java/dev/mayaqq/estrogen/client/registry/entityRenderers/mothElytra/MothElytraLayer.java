package dev.mayaqq.estrogen.client.registry.entityRenderers.mothElytra;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MothElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation WINGS_LOCATION = Estrogen.id("textures/entity/moth_elytra.png");
    private final MothElytraModel<T> elytraModel;

    public MothElytraLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.elytraModel = new MothElytraModel<>(modelSet.bakeLayer(MothElytraModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if (!itemStack.is(EstrogenItems.MOTH_ELYTRA.get())) {
            return;
        }
        poseStack.pushPose();
        String name = itemStack.getDisplayName().getString().replace("[", "").replace("]", "");
        if (name.equalsIgnoreCase("big")) {
            poseStack.scale(2f, 2f, 2f);
        } else if (name.equalsIgnoreCase("small")) {
            poseStack.scale(1f, 1f, 1f);
        } else {
            poseStack.scale(1.5f, 1.5f, 1.5f);
        }
        poseStack.translate(0.0f, 0.15f, -0.05f);
        this.getParentModel().copyPropertiesTo(this.elytraModel);
        this.elytraModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(WINGS_LOCATION), false, itemStack.hasFoil());
        this.elytraModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }
}
