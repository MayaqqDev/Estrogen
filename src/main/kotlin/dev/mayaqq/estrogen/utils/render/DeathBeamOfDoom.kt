package dev.mayaqq.estrogen.utils.render

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.mayaqq.cynosure.client.utils.color
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.helpers.McLevel
import invoke.kitty.kritter.utils.color.Color
import kotlin.math.cos
import kotlin.math.sin

//TODO: someone please figure this one out for me math is hard

object DeathBeamOfDoom {
    fun render(
        stack: PoseStack,
        consumer: VertexConsumer,
        radius: Float,
        color: Color,
        minY: Float = McLevel!!.minBuildHeight.toFloat(),
        maxY: Float = 512F
    ) {
        val segments = 32

        stack.pushPop {
            val last = this.last()
            val pose = last.pose()

            /*          4----3
             *          |    |
             *          |    |
             *          1----2
             */

            for (i in 0 until segments) {
                val x1 = radius * cos(2 * Math.PI * i / segments).toFloat()
                val z1 = radius * sin(2 * Math.PI * i / segments).toFloat()

                val x2 = radius * cos(2 * Math.PI * (i + 1) / segments).toFloat()
                val z2 = radius * sin(2 * Math.PI * (i + 1) / segments).toFloat()

                consumer.addVertex(pose, x1, minY, z1)
                    .color(color)
                    .setNormal(last, x1 / radius, minY, z1 / radius)
                consumer.addVertex(pose, x2, minY, z2)
                    .color(color)
                    .setNormal(last, x2 / radius, minY, z2 / radius)
                consumer.addVertex(pose, x2, maxY, z2)
                    .color(color)
                    .setNormal(last, x2 / radius, maxY, z2 / radius)
                consumer.addVertex(pose, x1, maxY, z1)
                    .color(color)
                    .setNormal(last, x1 / radius, maxY, z1 / radius)
            }
        }
    }
}