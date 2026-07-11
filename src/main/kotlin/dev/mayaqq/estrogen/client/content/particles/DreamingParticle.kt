package dev.mayaqq.estrogen.client.content.particles

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle

class DreamingParticle(
    level: ClientLevel, x: Double, y: Double, z: Double,
    xSpeed: Double, ySpeed: Double, zSpeed: Double,
    private val sprites: SpriteSet
) : TextureSheetParticle(level, x, y, z, xSpeed, ySpeed, zSpeed) {

    init {
        setParticleSpeed(0.0, 0.03, 0.0)
        lifetime = 16
        alpha = Math.clamp(random.nextFloat(), 0.2F, 1F)
        this.setSpriteFromAge(sprites)
    }

    override fun getFacingCameraMode(): FacingCameraMode = FacingCameraMode.LOOKAT_XYZ

    override fun tick() {
        super.tick()
        setSpriteFromAge(sprites)
    }

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT
}