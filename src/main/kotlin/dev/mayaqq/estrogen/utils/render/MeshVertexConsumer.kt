package dev.mayaqq.estrogen.utils.render

import com.mojang.blaze3d.vertex.VertexConsumer
import dev.engine_room.flywheel.api.model.Mesh
import dev.engine_room.flywheel.lib.memory.MemoryBlock
import dev.engine_room.flywheel.lib.model.SimpleQuadMesh
import dev.engine_room.flywheel.lib.vertex.VertexView
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.texture.OverlayTexture
import kotlin.math.max

class MeshVertexConsumer(
    private val viewFactory: () -> VertexView
) : VertexConsumer, AutoCloseable {

    private val view: VertexView = viewFactory()
    private lateinit var data: MemoryBlock
    private var active: Boolean = false
    private var pos: Int = -1

    fun begin(initialCapacity: Int) {
        if (active) error("vertex consumer already building")
        active = true
        view.vertexCount(initialCapacity)
        data = MemoryBlock.malloc(initialCapacity * view.stride())
        view.ptr(data.ptr())
        pos = -1
    }

    override fun addVertex(p0: Float, p1: Float, p2: Float): VertexConsumer {
        require(active)
        pos++
        if(pos >= view.vertexCount()) {
            val newCapacity = view.vertexCount() + 4
            val newData = MemoryBlock.malloc(view.stride() * newCapacity)
            data.copyTo(newData)
            data.free()
            view.vertexCount(newCapacity)
            view.ptr(newData.ptr())
            data = newData
        }

        view.x(pos, p0)
        view.y(pos, p1)
        view.z(pos, p2)

        return this
    }

    override fun setColor(p0: Int, p1: Int, p2: Int, p3: Int): VertexConsumer = setColor((p0 / 255f), (p1 / 255f), (p1 / 255f), (p2 / 255f))


    override fun setColor(p0: Float, p1: Float, p2: Float, p3: Float): VertexConsumer {
        view.r(pos, p0)
        view.g(pos, p1)
        view.b(pos, p2)
        view.a(pos, p3)
        return this
    }

    override fun setUv(p0: Float, p1: Float): VertexConsumer {
        view.u(pos, p0)
        view.v(pos, p1)
        return this
    }

    override fun setUv1(p0: Int, p1: Int): VertexConsumer = setOverlay(OverlayTexture.pack(p0, p1))

    override fun setOverlay(p0: Int): VertexConsumer {
        view.overlay(pos, p0)
        return this
    }

    override fun setUv2(p0: Int, p1: Int): VertexConsumer = setLight(LightTexture.pack(p0, p1))

    override fun setLight(p0: Int): VertexConsumer {
        view.light(pos, p0)
        return this
    }

    override fun setNormal(p0: Float, p1: Float, p2: Float): VertexConsumer {
        view.normalX(pos, p0)
        view.normalY(pos, p1)
        view.normalZ(pos, p2)
        return this
    }

    fun reset(freeData: Boolean) {
        pos = -1
        view.vertexCount(0)
        view.ptr(0L)
        data.free()
        active = false
    }

    fun build(): Mesh {
        require(active)
        val buildData = MemoryBlock.mallocTracked(view.stride() * (pos + 1))
        data.copyTo(buildData)
        val outputView = viewFactory()
        outputView.vertexCount(pos)
        outputView.load(buildData)
        return SimpleQuadMesh(outputView).also { reset(false) }
    }

    override fun close() {
        reset(true)
    }
}