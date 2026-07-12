@file:UseSerializers(ColorIntSerializer::class)
package dev.mayaqq.estrogen.content.particles

import dev.mayaqq.estrogen.content.EstrogenParticles
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.ColorIntSerializer
import invoke.kitty.kritter.utils.particles.SerializableParticleOptions
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.minecraft.core.particles.ParticleType

@Serializable
data class ColoredCloudParticleOptions(val color: Color, val emissive: Boolean) : SerializableParticleOptions<ColoredCloudParticleOptions> {

    override fun getType(): ParticleType<ColoredCloudParticleOptions> = EstrogenParticles.ColoredCloud
}