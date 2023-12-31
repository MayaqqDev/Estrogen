package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import dev.mayaqq.estrogen.utils.Time;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;

import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

@Environment(EnvType.CLIENT)
public class BoobFeatureRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public BoobFeatureRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> context) {
        super(context);
    }

public void render(PoseStack stack, MultiBufferSource bufferSource, int i, AbstractClientPlayer entity, float f, float g, float h, float j, float k, float l) {
        if (entity.hasEffect(EstrogenEffects.ESTROGEN_EFFECT) && Estrogen.CONFIG().chestFeature && entity.isSkinLoaded() && !entity.isInvisible()) {
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entitySolid(entity.getSkinTextureLocation()));
            int m = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
            stack.pushPose();
            float size;
            double startTime = entity.getAttributeValue(BOOB_GROWING_START_TIME.get());
            if (startTime >= 0.0) {
                float initialSize = (float) entity.getAttributeValue(BOOB_INITIAL_SIZE.get());
                double currentTime = Time.currentTime(entity.level());
                size = boobSize(startTime, currentTime, initialSize, h);
            } else {
                size = 0.0F;
            }
            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobs(stack, vertexConsumer, i, m, entity, size);

            ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.CHEST);
            if (itemStack.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getEquipmentSlot() == EquipmentSlot.CHEST) {
                    boolean bl = false;
                    boolean bl2 = itemStack.hasFoil();
                    if (armorItem instanceof DyeableArmorItem dyeable) {
                        int n = dyeable.getColor(itemStack);
                        float o = (float) (n >> 16 & 255) / 255.0F;
                        float p = (float) (n >> 8 & 255) / 255.0F;
                        float q = (float) (n & 255) / 255.0F;

                        ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmor(stack, bufferSource, i, armorItem, bl2, bl, o, p, q, (String) null, entity, size);
                        ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmor(stack, bufferSource, i, armorItem, bl2, bl, 1.0F, 1.0F, 1.0F, "overlay", entity, size);
                    } else {
                        ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmor(stack, bufferSource, i, armorItem, bl2, bl, 1.0F, 1.0F, 1.0F, (String) null, entity, size);
                    }
                }
            }

            stack.popPose();
        }
    }
}