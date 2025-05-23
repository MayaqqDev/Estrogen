package dev.mayaqq.estrogen.client.content.baubles

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import earth.terrarium.baubly.client.BaubleRenderer
import earth.terrarium.baubly.common.SlotInfo
import net.minecraft.client.Minecraft
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
//TODO: Implement this on the item
class EstrogenPatchesRenderer : BaubleRenderer {
    override fun render(
        stack: ItemStack,
        slotInfo: SlotInfo,
        pose: PoseStack,
        model: EntityModel<out LivingEntity>,
        buffer: MultiBufferSource,
        light: Int,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTicks: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        if (model is PlayerModel<out LivingEntity?>) {
            if (!EstrogenClientConfig.Accessories.renderEstrogenPatches) return
            val player = Minecraft.getInstance().player ?: return
            pose.pushPop {
                val leg: ModelPart = model.leftLeg

                // Sync with the player's animation
                if (player.isCrouching && !model.riding && !player.isSwimming) {
                    translate(0.0f, 0.0f, 0.25f)
                }
                translate(0.125f, 0.75f, 0.0f)
                mulPose(Axis.ZP.rotation(leg.zRot))
                mulPose(Axis.YP.rotation(leg.yRot))
                mulPose(Axis.XP.rotation(leg.xRot))
                translate(0.0f, 0.75f, 0.0f)

                // Rotate the patch to be on the player's thigh side
                mulPose(Axis.YN.rotation(89.6f))

                // move the patch to be on the player's thigh
                translate(0.0f, -0.6f, -0.135f)

                // Make the patch smaller
                scale(0.3f, 0.3f, 0.3f)

                Minecraft.getInstance().itemRenderer.renderStatic(
                    stack,
                    ItemDisplayContext.FIXED,
                    light,
                    light,
                    pose,
                    buffer,
                    player.level(),
                    0
                )
            }
        }
    }
}