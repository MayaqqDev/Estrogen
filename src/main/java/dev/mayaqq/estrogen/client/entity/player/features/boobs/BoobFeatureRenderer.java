package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import dev.mayaqq.estrogen.utils.Time;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;

import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

@Environment(EnvType.CLIENT)
public class BoobFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public BoobFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l) {
        if (abstractClientPlayerEntity.hasStatusEffect(EstrogenEffects.ESTROGEN_EFFECT) && Estrogen.getConfig().chestFeature && abstractClientPlayerEntity.hasSkinTexture() && !abstractClientPlayerEntity.isInvisible()) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(abstractClientPlayerEntity.getSkinTexture()));
            int m = LivingEntityRenderer.getOverlay(abstractClientPlayerEntity, 0.0F);
            matrixStack.push();
            float size;
            double startTime = abstractClientPlayerEntity.getAttributeValue(BOOB_GROWING_START_TIME);
            if (startTime >= 0.0) {
                float initialSize = (float) abstractClientPlayerEntity.getAttributeValue(BOOB_INITIAL_SIZE);
                double currentTime = Time.currentTime(abstractClientPlayerEntity.getWorld());
                size = boobSize(startTime, currentTime, initialSize, h);
            } else {
                size = 0.0F;
            }
            ((PlayerEntityModelExtension) this.getContextModel()).estrogen$renderBoobs(matrixStack, vertexConsumer, i, m, abstractClientPlayerEntity, size);

            ItemStack itemStack = abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.CHEST);
            if (itemStack.getItem() instanceof ArmorItem) {
                ArmorItem armorItem = (ArmorItem) itemStack.getItem();
                if (armorItem.getArmorSlot().getEquipmentSlot() == EquipmentSlot.CHEST) {
                    boolean bl = false;
                    boolean bl2 = itemStack.hasGlint();
                    if (armorItem instanceof DyeableArmorItem) {
                        int n = ((DyeableArmorItem) armorItem).getColor(itemStack);
                        float o = (float) (n >> 16 & 255) / 255.0F;
                        float p = (float) (n >> 8 & 255) / 255.0F;
                        float q = (float) (n & 255) / 255.0F;

                        ((PlayerEntityModelExtension) this.getContextModel()).estrogen$renderBoobArmor(matrixStack, vertexConsumerProvider, i, armorItem, bl2, bl, o, p, q, (String) null, abstractClientPlayerEntity, size);
                        ((PlayerEntityModelExtension) this.getContextModel()).estrogen$renderBoobArmor(matrixStack, vertexConsumerProvider, i, armorItem, bl2, bl, 1.0F, 1.0F, 1.0F, "overlay", abstractClientPlayerEntity, size);
                    } else {
                        ((PlayerEntityModelExtension) this.getContextModel()).estrogen$renderBoobArmor(matrixStack, vertexConsumerProvider, i, armorItem, bl2, bl, 1.0F, 1.0F, 1.0F, (String) null, abstractClientPlayerEntity, size);
                    }
                }
            }

            matrixStack.pop();
        }
    }
}