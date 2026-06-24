package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.client.particles.provider
import dev.mayaqq.cynosure.particles.CynosureParticleType
import dev.mayaqq.cynosure.particles.particleType
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.particles.DashTrailParticle
import dev.mayaqq.estrogen.client.content.particles.MothFuzzParticle
import dev.mayaqq.estrogen.content.particles.DashTrailParticleOptions
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.core.registries.Registries
import uwu.serenity.kritter.api.Registrar

//@EventSubscriber(env = [Environment.CLIENT])
object EstrogenParticles : Registrar<ParticleType<*>> by Estrogen..Registries.PARTICLE_TYPE {

    val MothFuzz: SimpleParticleType by particleType("moth_fuzz") {
        provider { sprites -> ParticleProvider { _, clientLevel, x, y, z, _, _, _ -> MothFuzzParticle(clientLevel, x, y, z, sprites) } }
    }

    val DashTrail: CynosureParticleType<DashTrailParticleOptions> by particleType(
        "dash_trail",
        DashTrailParticleOptions.CODEC,
        DashTrailParticleOptions.NETWORK_CODEC
    ) {
        provider(::DashTrailParticle)
        overrideLimiter = true
    }

    // TODO: Implement dream block ripples from walking on punching
    val DreamBlockRipple: SimpleParticleType by particleType("dream_ripple") {
        overrideLimiter = true
    }

}