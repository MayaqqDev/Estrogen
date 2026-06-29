package dev.mayaqq.estrogen.client.content.particles

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import com.mojang.math.Axis
import dev.mayaqq.cynosure.client.utils.lastPose
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.content.particles.DashTrailParticleOptions
import invoke.kitty.kritter.utils.color.White
import invoke.kitty.kritter.utils.color.floatBlue
import invoke.kitty.kritter.utils.color.floatGreen
import invoke.kitty.kritter.utils.color.floatRed
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
                renderer.model.renderToBuffer(matrices, consumer, 0, OverlayTexture.NO_OVERLAY, (White withAlpha 255).toInt())
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

        val buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE)

        matrices.pushPop {
            translate(x, y + 1.5f, z)
            scale(-1.0f, -1.0f, 1.0f)
            mulPose(Axis.YP.rotationDegrees(yRot))

            val alpha = 1f - Mth.lerp(partialTicks, max((age - 1).toDouble(), 0.0).toFloat(), age.toFloat()) / lifetime
            for (i in 0..<vertexCount) {
                val v = i * ModelConsumer.STRIDE
                buffer.addVertex(lastPose, vertices[v], vertices[v + 1], vertices[v + 2])
                    .setUv(vertices[v + 3], vertices[v + 4])
                    .setColor(r, g, b, alpha)
                    .setLight(LightTexture.FULL_BRIGHT)
            }
        }

    }

    override fun getRenderType(): ParticleRenderType = RenderType

    private class ModelConsumer : VertexConsumer {
        var data: FloatArray = FloatArray(4 * STRIDE)
        private var position = 0
        var vertexCount: Int = 0
        private var capacity = 4

        override fun addVertex(x: Float, y: Float, z: Float): VertexConsumer {
            data[position] = x
            data[position + 1] = y
            data[position + 2] = z

            position += STRIDE
            vertexCount++
            if (vertexCount >= capacity) {
                capacity += 4
                val newData = FloatArray(capacity * STRIDE)
                System.arraycopy(data, 0, newData, 0, data.size)
                data = newData
            }

            return this
        }

        override fun setColor(red: Int, green: Int, blue: Int, alpha: Int): VertexConsumer = this

        override fun setUv(u: Float, v: Float): VertexConsumer {
            data[position + 3] = u
            data[position + 4] = v
            return this
        }

        override fun setUv1(u: Int, v: Int): VertexConsumer = this

        override fun setUv2(u: Int, v: Int): VertexConsumer = this

        override fun setNormal(x: Float, y: Float, z: Float): VertexConsumer = this

        companion object {
            const val STRIDE: Int = 5
        }
    }

    object RenderType : ParticleRenderType {
        override fun begin(builder: Tesselator, textureManager: TextureManager): BufferBuilder {
            RenderSystem.depthMask(true)
            RenderSystem.enableCull()
            RenderSystem.enableBlend()
            RenderSystem.defaultBlendFunc()
            RenderSystem.setShader(EstrogenRenderer::dashTrailParticleShader)
            EstrogenRenderer.beginShaderpackBypass()

            //TODO: idfk which vertex format they remove those like in the next version
            return builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.NEW_ENTITY)
        }

        /* TODO:
        override fun end(tesselator: Tesselator) {
            RenderSystem.setShader(GameRenderer::getParticleShader)
            EstrogenRenderer.endShaderpackBypass()
        }
         */

        override fun toString(): String = "DashPlayerParticle"
    }
}