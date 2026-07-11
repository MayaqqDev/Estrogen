@file:OptIn(ExperimentalUuidApi::class)

package dev.mayaqq.estrogen.content.particles

import dev.mayaqq.estrogen.content.EstrogenParticles
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.ColorIntSerializer
import invoke.kitty.kritter.utils.color.ColorStringSerializer
import invoke.kitty.kritter.utils.particles.SerializableParticleOptions
import kotlinx.serialization.Serializable
import net.minecraft.core.particles.ParticleType
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@Serializable
data class DashTrailParticleOptions(val player: Uuid, val color: @Serializable(ColorIntSerializer::class) Color) : SerializableParticleOptions<DashTrailParticleOptions> {
    override fun getType(): ParticleType<DashTrailParticleOptions> = EstrogenParticles.DashTrail
}
