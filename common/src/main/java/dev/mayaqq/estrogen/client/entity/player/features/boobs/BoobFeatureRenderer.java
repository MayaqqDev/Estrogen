package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.client.Dash;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.utils.Time;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;

import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

@Environment(EnvType.CLIENT)
public class BoobFeatureRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private final TextureAtlas armorTrimAtlas;

    public BoobFeatureRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> context, ModelManager modelManager) {
        super(context);
        this.armorTrimAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    public void render(PoseStack stack, MultiBufferSource bufferSource, int i, AbstractClientPlayer entity, float f, float g, float h, float j, float k, float l) {
        ChestConfig chestConfig = ((PlayerEntityExtension) entity).estrogen$getChestConfig();
        if (entity.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get()) && EstrogenConfig.client().chestFeatureRendering.get() && chestConfig != null && chestConfig.enabled() && entity.isSkinLoaded() && !entity.isInvisible()) {
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entitySolid(entity.getSkinTextureLocation()));
            int m = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
            stack.pushPose();
            float size;
            double startTime = entity.getAttributeValue(BOOB_GROWING_START_TIME.get());
            double currentTime = Time.currentTime(entity.level());
            if (startTime >= 0.0) {
                float initialSize = (float) entity.getAttributeValue(BOOB_INITIAL_SIZE.get());
                size = boobSize(startTime, currentTime, initialSize, h);
            } else {
                size = 0.0F;
            }
            float yOffset = 0;
            if (EstrogenConfig.client().chestPhysicsRendering.get() && chestConfig.physicsEnabled()) {
                Physics physics = Dash.physicsMap.computeIfAbsent(entity.getUUID(), uuid -> new Physics());
                if (physics.active) {
                    size += physics.interpolate(currentTime, h).x;
                    yOffset = physics.interpolate(currentTime, h).y;
                }
            }
            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobs(stack, vertexConsumer, i, m, entity, size, yOffset);

            if (EstrogenConfig.client().chestArmorRendering.get() && chestConfig.armorEnabled()) {
                ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.CHEST);
                if (itemStack.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.getEquipmentSlot() == EquipmentSlot.CHEST) {
                        boolean bl2 = itemStack.hasFoil();
                        if (armorItem instanceof DyeableArmorItem dyeable) {
                            int n = dyeable.getColor(itemStack);
                            float o = (float) (n >> 16 & 255) / 255.0F;
                            float p = (float) (n >> 8 & 255) / 255.0F;
                            float q = (float) (n & 255) / 255.0F;

                            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmor(stack, bufferSource, i, bl2, o, p, q, null, entity, size, yOffset);
                            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmor(stack, bufferSource, i, bl2, 1.0F, 1.0F, 1.0F, "overlay", entity, size, yOffset);
                        } else {
                            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmor(stack, bufferSource, i, bl2, 1.0F, 1.0F, 1.0F, null, entity, size, yOffset);
                        }
                        ArmorTrim.getTrim(entity.level().registryAccess(), itemStack).ifPresent((armorTrim) -> {
                            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmorTrim(stack, bufferSource, i, bl2, armorTrim, armorItem.getMaterial(), this.armorTrimAtlas);
                        });
                    }
                }
            }

            stack.popPose();
        }
    }
}