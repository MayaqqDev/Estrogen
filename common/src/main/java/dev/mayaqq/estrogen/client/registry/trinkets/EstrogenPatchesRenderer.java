package dev.mayaqq.estrogen.client.registry.trinkets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import earth.terrarium.baubly.client.BaubleRenderer;
import earth.terrarium.baubly.client.BaublyClient;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class EstrogenPatchesRenderer implements BaubleRenderer {

    public EstrogenPatchesRenderer() {}

    @Override
    public void render(
            ItemStack stack,
            SlotInfo slotContext,
            PoseStack matrixStack,
            EntityModel<? extends LivingEntity> entityModel,
            MultiBufferSource renderTypeBuffer,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        if (entityModel instanceof PlayerModel<? extends LivingEntity> playerModel) {
            if (!EstrogenConfig.client().estrogenPatchRender.get()) return;
            LocalPlayer player = Minecraft.getInstance().player;
            matrixStack.pushPose();
            ModelPart leg = playerModel.leftLeg;

            // Sync with the player's animation
            if (player.isCrouching() && !playerModel.riding && !player.isSwimming()) {
                matrixStack.translate(0.0F, 0.0F, 0.25F);
            }
            matrixStack.translate(0.125F, 0.75F, 0.0F);
            matrixStack.mulPose(Axis.ZP.rotation(leg.zRot));
            matrixStack.mulPose(Axis.YP.rotation(leg.yRot));
            matrixStack.mulPose(Axis.XP.rotation(leg.xRot));
            matrixStack.translate(0.0F, 0.75F, 0.0F);

            // Rotate the patch to be on the player's thigh side
            matrixStack.mulPose(Axis.YN.rotation(89.6F));

            // move the patch to be on the player's thigh
            matrixStack.translate(0.0F, -0.6F, -0.135F);

            // Make the patch smaller
            matrixStack.scale(0.3F, 0.3F, 0.3F);

            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, light, matrixStack, renderTypeBuffer, player.level(), 0);
            matrixStack.popPose();
        }
    }
}