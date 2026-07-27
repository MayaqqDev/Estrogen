package dev.mayaqq.estrogen.client.cosmetics

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import invoke.kitty.kritter.utils.color.White
import net.minecraft.client.model.EntityModel
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.util.Mth
import net.minecraft.world.entity.Pose
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import org.joml.Vector2d
import kotlin.math.acos
import kotlin.math.max
import kotlin.math.sign
import kotlin.math.sqrt

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

        if (player.isFallFlying) reverseFallFly(stack, player as AbstractClientPlayer, partialTick)
        else if (player.getSwimAmount(partialTick) > 0.0) reverseSwimming(stack, player as AbstractClientPlayer, partialTick)
        //cancelNormalTransform(stack, player as AbstractClientPlayer, partialTick)

        stack.mulPose(Axis.XP.rotationDegrees(180F))
        stack.scale(0.75f, 0.75f, 0.75f)

        val hDiff = Vector2d.distance(player.xOld, player.zOld, player.x, player.z)
        val yDiff = player.yOld - player.position().y

        val y = max(Mth.lerp(0.3, yDiff, 0.0), 0.0) * 1.5f
        val z = -Mth.lerp(0.3, hDiff, 0.0) * 1.25f

        stack.translate(0.3, 0.0, z - 1.3)

        val defaultAnimation: Boolean = cosmetic.animation == null

        stack.translate(0.0, if (defaultAnimation) (Mth.sin(ageInTicks / 10) / 4) - yDiff + y else .125 - yDiff + y, 0.0)

        if (defaultAnimation) {
            stack.translate(0.5f, 0.5f, 0.5f)
            stack.mulPose(Axis.YP.rotationDegrees((ageInTicks * 1) % 360f))
            stack.translate(-0.5f, -0.5f, -0.5f)
        }

        cosmetic.render(
            buffer, //EstrogenRenderer.getCelShaded(buffer), TODO: A less shit outline system
            EstrogenRenderTypes::entityCutoutNoDiffuse,
            stack, White, packedLight, OverlayTexture.NO_OVERLAY
        )
        stack.popPose()
    }

    private fun reverseFallFly(stack: PoseStack, entity: AbstractClientPlayer, partialTick: Float) {
        val xRot = entity.getViewXRot(partialTick)
        val fallFlyingTicks: Float = entity.fallFlyingTicks.toFloat() + partialTick
        val clampedFallFlying = Mth.clamp(fallFlyingTicks * fallFlyingTicks / 100.0f, 0.0f, 1.0f)

        val viewVector: Vec3 = entity.getViewVector(partialTick)
        val deltaMovementLerped: Vec3 = entity.getDeltaMovementLerped(partialTick)
        val horizontalDistanceDeltaMovement = deltaMovementLerped.horizontalDistanceSqr()
        val horizontalDistanceViewVector = viewVector.horizontalDistanceSqr()
        if (horizontalDistanceDeltaMovement > 0.0 && horizontalDistanceViewVector > 0.0) {
            val thingimabob1 = (deltaMovementLerped.x * viewVector.x + deltaMovementLerped.z * viewVector.z) / sqrt(horizontalDistanceDeltaMovement * horizontalDistanceViewVector)
            val thingimabob2 = deltaMovementLerped.x * viewVector.z - deltaMovementLerped.z * viewVector.x
            stack.mulPose(Axis.YP.rotation((sign(thingimabob2) * acos(thingimabob1)).toFloat()))
        }

        if (!entity.isAutoSpinAttack) {
            stack.mulPose(Axis.XP.rotationDegrees(clampedFallFlying * (-90.0f - xRot)))
        }

        stack.translate(0.0f, -0.5f, 0.6f)
    }

    private fun reverseSwimming(stack: PoseStack, entity: AbstractClientPlayer, partialTick: Float) {
        val swimAmount = entity.getSwimAmount(partialTick)
        val xRot = entity.getViewXRot(partialTick)
        val waterTransform = if (entity.isInWater) -90.0f - xRot else -90.0f
        val lerpedTransform = Mth.lerp(swimAmount, 0.0f, waterTransform)
        stack.mulPose(Axis.XP.rotationDegrees(lerpedTransform))
        if (entity.isVisuallySwimming) stack.translate(0.0f, -1.0f, 0.3f)
    }

    private fun cancelNormalTransform(stack: PoseStack, entity: AbstractClientPlayer, partialTick: Float) {
        val yBodyRot = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot)

        if (!entity.hasPose(Pose.SLEEPING)) {
            stack.mulPose(Axis.YP.rotationDegrees(180.0f - yBodyRot))
        }
    }
}