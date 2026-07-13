package dev.mayaqq.estrogen.utils.render

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.engine_room.flywheel.lib.model.baked.EmptyVirtualBlockGetter
import dev.engine_room.flywheel.lib.model.baked.PartialModel
import dev.engine_room.flywheel.lib.util.RendererReloadCache
import dev.mayaqq.cynosure.client.models.baked.Mesh
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.utils.normalized
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.White
import invoke.kitty.kritter.utils.getValue
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.Blocks
import org.joml.Vector3f
import org.joml.Vector4f

private val LOCAL_RANDOM: RandomSource? by ThreadLocal.withInitial { RandomSource.create(42L) }

private val PARTIAL_MESH_CACHE: RendererReloadCache<PartialModel, Mesh> = RendererReloadCache { it.get().buildMesh() }

fun BakedModel.buildMesh(): Mesh {
    val meshBuilder = MeshBuilder()
    val poseStack = PoseStack()
    McClient.blockRenderer.modelRenderer.tesselateBlock(
        EmptyVirtualBlockGetter.FULL_BRIGHT,
        this,
        Blocks.AIR.defaultBlockState(),
        BlockPos.ZERO,
        poseStack,
        meshBuilder,
        false,
        LOCAL_RANDOM!!,
        42L,
        OverlayTexture.NO_OVERLAY
    )
    return meshBuilder.build()
}

val PartialModel.mesh: Mesh
    get() = PARTIAL_MESH_CACHE[this]

fun Mesh.render(
    buffer: VertexConsumer,
    matrices: PoseStack,
    color: Color = White,
    light: Int = LightTexture.FULL_BRIGHT,
    overlay: Int = OverlayTexture.NO_OVERLAY
) {
    val posVec = Vector4f()
    val normalVec = Vector3f()

    for(i in 0..<vertexCount) {
        posVec.set(x(i), y(i), z(i))
            .mul(matrices.last().pose())
        normalVec.set(normalX(i), normalY(i), normalZ(i))
            .mul(matrices.last().normal())

        buffer.addVertex(
            posVec.x, posVec.y, posVec.z,
            color.toInt(),
            u(i), v(i),
            overlay, light,
            normalVec.x, normalVec.y, normalVec.z
        )
    }
}

class MeshBuilder : VertexConsumer {
    private var data: IntArray = IntArray(32 * STRIDE)
    private var position = -STRIDE
    var vertexCount: Int = 0
        private set
    private var capacity = 32

    override fun addVertex(x: Float, y: Float, z: Float): VertexConsumer {
        position += STRIDE
        vertexCount++
        if (vertexCount >= capacity) {
            capacity += 6
            val newData = IntArray(capacity * STRIDE)
            System.arraycopy(data, 0, newData, 0, data.size)
            data = newData
            Mesh
        }
        data[position] = x.toBits()
        data[position + 1] = y.toBits()
        data[position + 2] = z.toBits()
        return this
    }

    override fun setColor(red: Int, green: Int, blue: Int, alpha: Int): VertexConsumer = this

    override fun setUv(u: Float, v: Float): VertexConsumer {
        data[position + 3] = u.toBits()
        data[position + 4] = v.toBits()
        return this
    }

    override fun setUv1(u: Int, v: Int): VertexConsumer = this

    override fun setUv2(u: Int, v: Int): VertexConsumer = this

    override fun setNormal(x: Float, y: Float, z: Float): VertexConsumer {
        data[position + 5] = packNormal(x, y, z)
        return this
    }

    // override fun setColor(defaultR: Float, defaultG: Float, defaultB: Float, defaultA: Float) {}

    // override fun unsetDefaultColor() {}

    fun build(): Mesh {
        return Mesh(data.sliceArray(0..<(vertexCount * STRIDE)))
    }

    companion object {
        const val STRIDE: Int = 6

        private fun packNormal(x: Float, y: Float, z: Float): Int =
            ((x.normalized() * 127f).toInt() and 0xFF) or (((y.normalized() * 127f).toInt() and 0xFF) shl 8) or ((((z.normalized()) * 127f).toInt() and 0xFF) shl 16)
    }
}