package dev.mayaqq.estrogen.client.content.particles

import com.mojang.blaze3d.vertex.VertexConsumer
import dev.engine_room.flywheel.api.task.Plan
import dev.engine_room.flywheel.api.visual.DynamicVisual
import dev.engine_room.flywheel.api.visual.Effect
import dev.engine_room.flywheel.api.visual.EffectVisual
import dev.engine_room.flywheel.api.visualization.VisualizationContext
import dev.engine_room.flywheel.lib.instance.ColoredLitInstance
import dev.engine_room.flywheel.lib.instance.ColoredLitOverlayInstance
import dev.engine_room.flywheel.lib.instance.OrientedInstance
import dev.engine_room.flywheel.lib.instance.TransformedInstance
import dev.engine_room.flywheel.lib.material.Materials
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual
import dev.engine_room.flywheel.lib.visualization.VisualizationHelper
import net.minecraft.client.Camera
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle
import net.minecraft.client.renderer.LightTexture
import net.minecraft.world.level.LevelAccessor
import org.joml.Quaternionf
import org.joml.Vector3f

class FallingStarParticle(
    level: ClientLevel, x: Double, y: Double, z: Double,
    xSpeed: Double, ySpeed: Double, zSpeed: Double,
    private val sprites: SpriteSet
) : TextureSheetParticle(level, x, y, z, xSpeed, ySpeed, zSpeed) {

    private var skipRender: Boolean = false

    init {
        setSpriteFromAge(sprites)
        quadSize = 1f
        lifetime = 10 * 20
        friction = 1f
        hasPhysics = false
        setParticleSpeed(0.0, ySpeed, 0.0)
        // VisualizationHelper.queueAdd(this) TODO: Optimization: Implement as flywheel effect (thats what skipRender is for)
    }

    override fun getFacingCameraMode(): FacingCameraMode = FacingCameraMode.LOOKAT_Y

    override fun tick() {
        setSpriteFromAge(sprites)
        super.tick()
    }

    override fun render(p0: VertexConsumer, p1: Camera, p2: Float) {
        if (!skipRender) super.render(p0, p1, p2)
    }

    override fun renderRotatedQuad(buffer: VertexConsumer, quaternion: Quaternionf, x: Float, y: Float, z: Float, partialTicks: Float) {
        val size = this.getQuadSize(partialTicks)
        val light = this.getLightColor(partialTicks)
        this.renderVertex(buffer, quaternion, x, y, z, 1.0f, -2.0f, size, u1, v1, light)
        this.renderVertex(buffer, quaternion, x, y, z, 1.0f, 2.0f, size, u1, v0, light)
        this.renderVertex(buffer, quaternion, x, y, z, -1.0f, 2.0f, size, u0, v0, light)
        this.renderVertex(buffer, quaternion, x, y, z, -1.0f, -2.0f, size, u0, v1, light)
    }

    private fun renderVertex(
        buffer: VertexConsumer,
        quaternion: Quaternionf,
        x: Float,
        y: Float,
        z: Float,
        xOffset: Float,
        yOffset: Float,
        quadSize: Float,
        u: Float,
        v: Float,
        packedLight: Int
    ) {
        val vec = Vector3f(xOffset, yOffset, 0.0f).rotate(quaternion).mul(quadSize).add(x, y, z)
        buffer.addVertex(vec.x(), vec.y(), vec.z()).setUv(u, v)
            .setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(packedLight)
    }

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT

    override fun getLightColor(p0: Float): Int = LightTexture.FULL_BRIGHT

}