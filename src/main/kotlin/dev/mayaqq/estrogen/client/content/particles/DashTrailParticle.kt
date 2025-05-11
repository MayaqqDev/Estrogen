package dev.mayaqq.estrogen.client.content.particles

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import com.mojang.math.Axis
import dev.mayaqq.cynosure.client.utils.lastPose
import dev.mayaqq.cynosure.utils.colors.floatBlue
import dev.mayaqq.cynosure.utils.colors.floatGreen
import dev.mayaqq.cynosure.utils.colors.floatRed
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.estrogen.content.particles.DashTrailParticleOptions
import dev.mayaqq.estrogen.id
import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import kotlin.math.max

class DashTrailParticle(
    level: ClientLevel,
    x: Double,
    y: Double,
    z: Double,
    entity: LivingEntity,
    private val r: Float,
    private val g: Float,
    private val b: Float
) : Particle(level, x, y, z) {

    private val matrices = PoseStack()
    private val vertices: FloatArray
    private val isLocalPlayer: Boolean
    private val yRot: Float
    private val vertexCount: Int

    constructor(options: DashTrailParticleOptions, level: ClientLevel, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double) : this(
        level, x, y, z,
        level.getPlayerByUUID(options.player)!!,
        options.color.floatRed,
        options.color.floatGreen,
        options.color.floatBlue
    )

    init {
        this.hasPhysics = false
        this.boundingBox = entity.boundingBox
        this.setLifetime(15)
        this.yRot = entity.yBodyRot + 180.0f
        this.isLocalPlayer = entity === Minecraft.getInstance().getCameraEntity()

        val renderer = Minecraft.getInstance().entityRenderDispatcher.getRenderer(entity) as LivingEntityRenderer<*, *>
        val consumer = ModelConsumer()
        matrices.pushPop {
            renderer.model.young = entity.isBaby // Unbaby the player model
            renderer.model.renderToBuffer(matrices, consumer, 0, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f)
        }
        vertices = consumer.data
        vertexCount = consumer.vertexCount
    }

    override fun render(buffer: VertexConsumer, renderInfo: Camera, partialTicks: Float) {
        val pos = renderInfo.position
        if (isLocalPlayer && Minecraft.getInstance().options.cameraType.isFirstPerson && pos.distanceToSqr(x, y, z) < 4.0f) return
        val x = (this.x - pos.x()).toFloat()
        val y = (this.y - pos.y()).toFloat()
        val z = (this.z - pos.z()).toFloat()

        matrices.pushPop {
            translate(x, y + 1.5f, z)
            scale(-1.0f, -1.0f, 1.0f)
            mulPose(Axis.YP.rotationDegrees(yRot))

            val alpha = 1f - Mth.lerp(partialTicks, max((age - 1).toDouble(), 0.0).toFloat(), age.toFloat()) / lifetime
            for (i in 0..<vertexCount) {
                val v = i * ModelConsumer.STRIDE
                buffer.vertex(lastPose, vertices[v], vertices[v + 1], vertices[v + 2])
                    .uv(uForVertex(i), vForVertex(i))
                    .color(r, g, b, alpha)
                    .uv2(LightTexture.FULL_BRIGHT)
                    .endVertex()
            }
        }
    }

    override fun getRenderType(): ParticleRenderType = RENDER_TYPE

    private class ModelConsumer : VertexConsumer {
        var data: FloatArray = FloatArray(12)
        private var position = 0
        var vertexCount: Int = 0
        private var capacity = 4

        override fun vertex(x: Double, y: Double, z: Double): VertexConsumer {
            data[position] = x.toFloat()
            data[position + 1] = y.toFloat()
            data[position + 2] = z.toFloat()
            return this
        }

        override fun color(red: Int, green: Int, blue: Int, alpha: Int): VertexConsumer = this

        override fun uv(u: Float, v: Float): VertexConsumer = this

        override fun overlayCoords(u: Int, v: Int): VertexConsumer = this

        override fun uv2(u: Int, v: Int): VertexConsumer = this

        override fun normal(x: Float, y: Float, z: Float): VertexConsumer = this

        override fun endVertex() {
            position += STRIDE
            vertexCount++
            if (vertexCount >= capacity) {
                capacity += 4
                val newData = FloatArray(capacity * STRIDE)
                System.arraycopy(data, 0, newData, 0, data.size)
                data = newData
            }
        }

        override fun defaultColor(defaultR: Int, defaultG: Int, defaultB: Int, defaultA: Int) {}

        override fun unsetDefaultColor() {}

        companion object {
            const val STRIDE: Int = 3
        }
    }

    companion object {
        private val WHITE_TEXTURE: ResourceLocation = id("textures/misc/pixel.png")

        val RENDER_TYPE: ParticleRenderType = object : ParticleRenderType {
            override fun begin(builder: BufferBuilder, textureManager: TextureManager) {
                RenderSystem.depthMask(true)
                RenderSystem.setShaderTexture(0, WHITE_TEXTURE)
                RenderSystem.enableBlend()
                RenderSystem.defaultBlendFunc()
                builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE)
            }

            override fun end(tesselator: Tesselator) {
                tesselator.end()
            }

            override fun toString(): String {
                return "DashPlayerParticle"
            }
        }

        private fun uForVertex(v: Int): Float {
            val i = v % 4
            return if (i == 2 || i == 3) 1f else 0f
        }

        private fun vForVertex(v: Int): Float {
            return if ((v % 2) == 0) 1f else 0f
        }
    }
}