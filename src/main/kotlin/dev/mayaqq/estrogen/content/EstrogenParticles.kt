package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import invoke.kitty.kritter.registry.api.Registrar
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.registries.Registries

//@EventSubscriber(env = [Environment.CLIENT])
object EstrogenParticles : Registrar<ParticleType<*>> by Registrar(MOD_ID, Registries.PARTICLE_TYPE) {

    /* TODO:
    val MothFuzz: SimpleParticleType by particleType<>("moth_fuzz") {
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
     */

}