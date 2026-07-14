package dev.mayaqq.estrogen.client.cosmetics

import com.mojang.blaze3d.systems.RenderSystem
import dev.mayaqq.cynosure.client.models.ModelData
import dev.mayaqq.cynosure.client.models.ModelElement
import dev.mayaqq.cynosure.client.models.ModelElementFace
import dev.mayaqq.cynosure.client.models.ModelElementGroup
import dev.mayaqq.cynosure.client.models.baked.ModelRenderType
import net.minecraft.client.Minecraft
import net.minecraft.core.Direction
import org.joml.Vector3f
import org.joml.Vector3fc

object ModelOutlineGenerator {
    fun process(model: ModelData): ModelData {
        // TODO: Remap indices
        //if (model.groups.isNotEmpty()) return model

        val minmaxx = model.elements.minOf { it.from.x } to model.elements.maxOf { it.to.x }
        val minmaxy = model.elements.minOf { it.from.y } to model.elements.maxOf { it.to.y }
        val minmaxz = model.elements.minOf { it.from.z } to model.elements.maxOf { it.to.z }

        fun isOuterFace(dir: Direction, element: ModelElement, face: ModelElementFace): Boolean {
            val (min, max) = when (dir.axis) {
                Direction.Axis.X -> minmaxx
                Direction.Axis.Y -> minmaxy
                Direction.Axis.Z -> minmaxz
            }

            val vec = when (dir.axisDirection) {
                Direction.AxisDirection.POSITIVE -> element.to
                Direction.AxisDirection.NEGATIVE -> element.from
            }

            val coord = when (dir.axis) {
                Direction.Axis.X -> vec.x
                Direction.Axis.Y -> vec.y
                Direction.Axis.Z -> vec.z
            }

            return when (dir.axisDirection) {
                Direction.AxisDirection.NEGATIVE -> coord <= min
                Direction.AxisDirection.POSITIVE -> coord >= max
            }
        }

        val newElements = model.elements.map { element ->
            element.copy(
                from = Vector3f(element.to),
                to = Vector3f(element.from),
                //faces = element.faces.filter { (direction, face) -> isOuterFace(direction, element, face) },
                faces = element.faces.mapValues { (_, face) ->
                    face.copy(uv = floatArrayOf(face.uv[2], face.uv[3], face.uv[0], face.uv[1]))
                },
                shade = false
            )
        }


        return model.copy(renderType = ModelRenderType.CUTOUT, elements = newElements)
    }
}