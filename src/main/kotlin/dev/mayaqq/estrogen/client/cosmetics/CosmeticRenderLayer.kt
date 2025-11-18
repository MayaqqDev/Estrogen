package dev.mayaqq.estrogen.client.cosmetics

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.util.Mth
import net.minecraft.world.entity.player.Player
import org.joml.Vector2d
import kotlin.math.max

class CosmeticRenderLayer(renderer: RenderLayerParent<Player, EntityModel<Player>>) : RenderLayer<Player, EntityModel<Player>>(renderer) {
    override fun render(
        stack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        player: Player,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTick: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        val cosmetic: Cosmetic = player.getUUID().getCosmetic() ?: return

        stack.pushPose()
        stack.mulPose(Axis.XP.rotationDegrees(180F))
        stack.scale(0.75f, 0.75f, 0.75f)

        val hDiff = Vector2d.distance(player.xOld, player.zOld, player.x, player.z)
        val yDiff = player.yOld - player.position().y

        val y = max(Mth.lerp(0.3, yDiff, 0.0), 0.0) * 1.5f
        val z = -Mth.lerp(0.3, hDiff, 0.0) * 1.25f

        stack.translate(0.0, 0.0, z - 1)

        val defaultAnimation: Boolean = cosmetic.animation == null

        stack.translate(0.0, if (defaultAnimation) (Mth.sin(ageInTicks / 10) / 4) - yDiff + y else .125 - yDiff + y, 0.0)

        if (defaultAnimation) {
            stack.translate(0.5f, 0.5f, 0.5f)
            stack.mulPose(Axis.YP.rotationDegrees((ageInTicks * 1) % 360f))
            stack.translate(-0.5f, -0.5f, -0.5f)
        }

        cosmetic.render(
            RenderType::entityCutout,
            buffer,
            stack,
            packedLight,
            OverlayTexture.NO_OVERLAY
        )

        stack.popPose()
    }
}