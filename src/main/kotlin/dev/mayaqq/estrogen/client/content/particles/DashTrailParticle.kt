package dev.mayaqq.estrogen.client.content.particles

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import com.mojang.math.Axis
import dev.mayaqq.cynosure.client.utils.lastPose
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.content.particles.DashTrailParticleOptions
import invoke.kitty.kritter.utils.color.floatBlue
import invoke.kitty.kritter.utils.color.floatGreen
import invoke.kitty.kritter.utils.color.floatRed
import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import kotlin.math.max

//TODO: Fix the particle mess

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
    private val texture: ResourceLocation
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
        try {
            this.hasPhysics = false
            this.boundingBox = entity.boundingBox
            this.setLifetime(15)
            this.yRot = entity.yBodyRot + 180.0f
            this.isLocalPlayer = entity === Minecraft.getInstance().getCameraEntity()

            val renderer = Minecraft.getInstance()
                .entityRenderDispatcher
                .getRenderer(entity) as LivingEntityRenderer<LivingEntity, *>

            // TODO: ears compat (we'll need to depend on full ears not just api)
            val consumer = ModelConsumer()
            matrices.pushPop {
                renderer.model.young = entity.isBaby // Unbaby the player model
                renderer.model.renderToBuffer(matrices, consumer, 0, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f)
            }
            vertices = consumer.data
            vertexCount = consumer.vertexCount
            texture = renderer.getTextureLocation(entity)
        } catch (ex: Exception) {
            // FUCKING MINECRFAft just catches all errors without logging anything useful by default
            // wrrrrrrr
            Estrogen.error("Error creating trail particle {}", this, ex)
            throw ex
        }
    }

    override fun render(consumer: VertexConsumer, renderInfo: Camera, partialTicks: Float) {
        val pos = renderInfo.position
        if (isLocalPlayer && Minecraft.getInstance().options.cameraType.isFirstPerson && pos.distanceToSqr(x, y, z) < 4.0f)
            return

        val x = (this.x - pos.x()).toFloat()
        val y = (this.y - pos.y()).toFloat()
        val z = (this.z - pos.z()).toFloat()

        RenderSystem.setShaderTexture(0, texture)

        val buffer = Tesselator.getInstance().builder
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE)

        matrices.pushPop {
            translate(x, y + 1.5f, z)
            scale(-1.0f, -1.0f, 1.0f)
            mulPose(Axis.YP.rotationDegrees(yRot))

            val alpha = 1f - Mth.lerp(partialTicks, max((age - 1).toDouble(), 0.0).toFloat(), age.toFloat()) / lifetime
            for (i in 0..<vertexCount) {
                val v = i * ModelConsumer.STRIDE
                buffer.vertex(lastPose, vertices[v], vertices[v + 1], vertices[v + 2])
                    .uv(vertices[v + 3], vertices[v + 4])
                    .color(r, g, b, alpha)
                    .uv2(LightTexture.FULL_BRIGHT)
                    .endVertex()
            }
        }

        Tesselator.getInstance().end()
    }

    override fun getRenderType(): ParticleRenderType = RenderType

    private class ModelConsumer : VertexConsumer {
        var data: FloatArray = FloatArray(4 * STRIDE)
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

        override fun uv(u: Float, v: Float): VertexConsumer {
            data[position + 3] = u
            data[position + 4] = v
            return this
        }

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
            const val STRIDE: Int = 5
        }
    }

    object RenderType : ParticleRenderType {
        override fun begin(builder: BufferBuilder, textureManager: TextureManager) {
            RenderSystem.depthMask(true)
            RenderSystem.enableCull()
            RenderSystem.enableBlend()
            RenderSystem.defaultBlendFunc()
            RenderSystem.setShader(EstrogenRenderer::dashTrailParticleShader)
            EstrogenRenderer.beginShaderpackBypass()
        }

        override fun end(tesselator: Tesselator) {
            RenderSystem.setShader(GameRenderer::getParticleShader)
            EstrogenRenderer.endShaderpackBypass()
        }

        override fun toString(): String = "DashPlayerParticle"
    }
}