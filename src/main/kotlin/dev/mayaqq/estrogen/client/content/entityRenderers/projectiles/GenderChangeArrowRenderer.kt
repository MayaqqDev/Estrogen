package dev.mayaqq.estrogen.client.content.entityRenderers.projectiles

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.content.entities.GenderChangeArrow
import dev.mayaqq.estrogen.id
import invoke.kitty.kritter.utils.color.White
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.phys.Vec3
import org.joml.Vector3f

class GenderChangeArrowRenderer(context: EntityRendererProvider.Context) : EntityRenderer<GenderChangeArrow>(context) {
    override fun getTextureLocation(entity: GenderChangeArrow): ResourceLocation = GENDER_CHANGE_ARROW_LOCATION

    override fun render(arrow: GenderChangeArrow, yaw: Float, partialTicks: Float, poseStack: PoseStack, buffer: MultiBufferSource, light: Int) {
        if (arrow.shouldRenderTail()) {
            poseStack.pushPop {
                val anchor = arrow.getPosition(partialTicks)
                val points = mutableListOf<Vec3>().apply {
                    addAll(arrow.tailPoints)
                    addFirst(anchor)
                }.map { it.subtract(anchor) }
                val buffer = buffer.getBuffer(EstrogenRenderTypes.TRANS_LINES)
                val pose = poseStack.last()
                points.forEachIndexed { index, point ->
                    val next = if (index < points.size - 1) {
                        points[index + 1]
                    } else point

                    buffer.line(pose, point.toVector3f(), next.toVector3f(), light)
                }
                arrow.lastRemoved?.subtract(anchor)?.let { lastRemoved ->
                    val last = points.last()

                    val vector = lastRemoved.subtract(last)

                    val partial = 1.0 - partialTicks.toDouble()

                    val shortened = vector.multiply(partial, partial, partial)

                    val tailEnd = last.add(shortened)

                    buffer.line(pose, last.toVector3f(), tailEnd.toVector3f(), light)
                }
            }
        }

        poseStack.pushPop {
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, arrow.yRotO, arrow.yRot) - 90.0f))
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, arrow.xRotO, arrow.xRot)))
            val shake = arrow.shakeTime.toFloat() - partialTicks
            if (shake > 0.0f) {
                val shakeAmount = -Mth.sin(shake * 3.0f) * shake
                poseStack.mulPose(Axis.ZP.rotationDegrees(shakeAmount))
            }

            // rotate the whole arrow 90 Degrees so it's a bit less symmetrical
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f))

            poseStack.mulPose(Axis.XP.rotationDegrees(45.0f))
            poseStack.scale(0.05625f, 0.05625f, 0.05625f)
            poseStack.translate(-4.0f, 0.0f, 0.0f)
            val buffer = buffer.getBuffer(RenderType.entityCutout(getTextureLocation(arrow)))
            val pose = poseStack.last()
            // Tail back
            pose.vertex(buffer, -7, -2, -2, 0.0f, 0.15625f, -1, 0, 0, light)
            pose.vertex(buffer, -7, -2, 2, 0.15625f, 0.15625f, -1, 0, 0, light)
            pose.vertex(buffer, -7, 2, 2, 0.15625f, 0.3125f, -1, 0, 0, light)
            pose.vertex(buffer, -7, 2, -2, 0.0f, 0.3125f, -1, 0, 0, light)
            // Tail front
            pose.vertex(buffer, -7, 2, -2, 0.0f, 0.3125f, 1, 0, 0, light)
            pose.vertex(buffer, -7, 2, 2, 0.15625f, 0.3125f, 1, 0, 0, light)
            pose.vertex(buffer, -7, -2, 2, 0.15625f, 0.15625f, 1, 0, 0, light)
            pose.vertex(buffer, -7, -2, -2, 0.0f, 0.15625f, 1, 0, 0, light)

            // Body
            repeat(2) {
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0f))
                pose.vertex(buffer, -8, -2, 0, 0.0f, 0.0f, 0, 1, 0, light)
                pose.vertex(buffer, 8, -2, 0, 0.5f, 0.0f, 0, 1, 0, light)
                pose.vertex(buffer, 8, 2, 0, 0.5f, 0.15625f, 0, 1, 0, light)
                pose.vertex(buffer, -8, 2, 0, 0.0f, 0.15625f, 0, 1, 0, light)

                pose.vertex(buffer, -8, 2, 0, 0.0f, 0.15625f, 0, -1, 0, light)
                pose.vertex(buffer, 8, 2, 0, 0.5f, 0.15625f, 0, -1, 0, light)
                pose.vertex(buffer, 8, -2, 0, 0.5f, 0.0f, 0, -1, 0, light)
                pose.vertex(buffer, -8, -2, 0, 0.0f, 0.0f, 0, -1, 0, light)
            }
        }
        super.render(arrow, yaw, partialTicks, poseStack, buffer, light)
    }

    fun PoseStack.Pose.vertex(
        consumer: VertexConsumer,
        x: Int,
        y: Int,
        z: Int,
        u: Float,
        v: Float,
        normalX: Int,
        normalY: Int,
        normalZ: Int,
        light: Int
    ) {
        consumer.addVertex(this, x.toFloat(), y.toFloat(), z.toFloat()).setColor(-1).setUv(u, v)
            .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light)
            .setNormal(this, normalX.toFloat(), normalZ.toFloat(), normalY.toFloat())
    }

    private fun VertexConsumer.line(pose: PoseStack.Pose, point: Vector3f, next: Vector3f, light: Int) {
        this.addVertex(pose,point).apply {
            setLight(light)
            setColor(White.toInt())
            setNormal(pose, point.x, point.y, point.z)
        }
        this.addVertex(pose, next).apply {
            setLight(light)
            setColor(White.toInt())
            setNormal(pose, next.x, next.y, next.z)
        }
    }

    companion object {
        val GENDER_CHANGE_ARROW_LOCATION = id("textures/entity/projectiles/gender_change_arrow.png")
    }
}