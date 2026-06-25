package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.particles.DashTrailParticle
import dev.mayaqq.estrogen.client.content.particles.MothFuzzParticle
import dev.mayaqq.estrogen.content.particles.DashTrailParticleOptions
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.misc.particleType
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.core.registries.Registries

//@EventSubscriber(env = [Environment.CLIENT])
object EstrogenParticles : Registrar<ParticleType<*>> by Registrar(MOD_ID, Registries.PARTICLE_TYPE) {

    val MothFuzz: SimpleParticleType by particleType("moth_fuzz") {
        provider { sprites -> ParticleProvider { _, cilientLevel, x, y, z, _, _, _ -> MothFuzzParticle(clientLevel, x, y, z, sprites) } }
    }i

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