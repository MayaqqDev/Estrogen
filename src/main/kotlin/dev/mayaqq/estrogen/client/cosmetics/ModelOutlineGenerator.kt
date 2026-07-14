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
        val newElements = model.elements.map { element ->
            element.copy(
                from = Vector3f(element.from).add(-.75f, -.75f, -.75f),
                to = Vector3f(element.to).add(.75f, .75f, .75f),
                faces = Direction.entries.associateWith { dir ->
                    val face = element.faces[dir]
                    face?.copy(uv = floatArrayOf(face.uv[2], face.uv[3], face.uv[0], face.uv[1]))
                        ?: ModelElementFace(floatArrayOf(0f, 1f, 0f, 1f), 0f, "meow")
                },
                shade = false
            )
        }


        return model.copy(renderType = ModelRenderType.CUTOUT, elements = newElements)
    }
}