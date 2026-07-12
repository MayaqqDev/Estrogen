package dev.mayaqq.estrogen.client.content.particles

import dev.mayaqq.estrogen.content.particles.ColoredCloudParticleOptions
import invoke.kitty.kritter.utils.color.floatBlue
import invoke.kitty.kritter.utils.color.floatGreen
import invoke.kitty.kritter.utils.color.floatRed
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.PlayerCloudParticle
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.renderer.LightTexture

class ColoredEmissiveCloudParticle(
    spriteSet: SpriteSet,
    options: ColoredCloudParticleOptions,
    level: ClientLevel,
    x: Double,
    y: Double,
    z: Double,
    xs: Double,
    ys: Double,
    zs: Double
) : PlayerCloudParticle(level, x, y, z, xs, ys, zs, spriteSet) {

    val emissive = options.emissive

    init {
        val multiplier = this.random.nextFloat() * 0.4f + 0.6f;
        fun randomizeColor(coordMultiplier: Float): Float =
            (this.random.nextFloat() * 0.2f + 0.8f) * coordMultiplier * multiplier

        rCol = randomizeColor(options.color.floatRed)
        gCol = randomizeColor(options.color.floatGreen)
        bCol = randomizeColor(options.color.floatBlue)
    }

    override fun getLightColor(p0: Float): Int =
        if (emissive) LightTexture.FULL_BRIGHT else super.getLightColor(p0)
}