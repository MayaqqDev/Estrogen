package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.client.features.boobs.BoobPhysicsManager;
import dev.mayaqq.estrogen.client.features.boobs.Physics;
import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import dev.mayaqq.estrogen.utils.Boob;
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
import org.jetbrains.annotations.NotNull;

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

    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, int i, @NotNull AbstractClientPlayer entity, float f, float g, float h, float j, float k, float l) {
        ChestConfig chestConfig = ((PlayerEntityExtension) entity).estrogen$getChestConfig();
        if (Boob.shouldShow(entity) && EstrogenConfig.client().chestFeatureRendering.get() && chestConfig != null && chestConfig.enabled() && entity.isSkinLoaded() && !entity.isInvisible()) {
            if (entity.getItemBySlot(EquipmentSlot.CHEST).is(EstrogenTags.Items.CHEST_FEATURE_DISABLED)) return;
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(entity.getSkinTextureLocation()));
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
                Physics physics = BoobPhysicsManager.getPhysicsForPlayer(entity);
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
                        boolean glint = itemStack.hasFoil();
                        if (armorItem instanceof DyeableArmorItem dyeable) {
                            int dyeColor = dyeable.getColor(itemStack);
                            float o = (float) (dyeColor >> 16 & 255) / 255.0F;
                            float p = (float) (dyeColor >> 8 & 255) / 255.0F;
                            float q = (float) (dyeColor & 255) / 255.0F;

                            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmor(stack, bufferSource, i, glint, o, p, q, false, entity, size, yOffset);
                            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmor(stack, bufferSource, i, glint, 1.0F, 1.0F, 1.0F, true, entity, size, yOffset);
                        } else {
                            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmor(stack, bufferSource, i, glint, 1.0F, 1.0F, 1.0F, false, entity, size, yOffset);
                        }
                        ArmorTrim.getTrim(entity.level().registryAccess(), itemStack).ifPresent((armorTrim) -> {
                            ((PlayerEntityModelExtension) this.getParentModel()).estrogen$renderBoobArmorTrim(stack, bufferSource, i, glint, armorTrim, armorItem.getMaterial(), this.armorTrimAtlas, entity);
                        });
                    }
                }
            }

            stack.popPose();
        }
    }
}