package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.particles.ColoredEmissiveCloudParticle
import dev.mayaqq.estrogen.client.content.particles.DashTrailParticle
import dev.mayaqq.estrogen.client.content.particles.DreamingParticle
import dev.mayaqq.estrogen.client.content.particles.FallingStarParticle
import dev.mayaqq.estrogen.client.content.particles.MothFuzzParticle
import dev.mayaqq.estrogen.content.particles.ColoredCloudParticleOptions
import dev.mayaqq.estrogen.content.particles.DashTrailParticleOptions
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.misc.factory
import invoke.kitty.kritter.registry.misc.particleType
import invoke.kitty.kritter.registry.misc.simpleParticleType
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.core.registries.Registries

object EstrogenParticles : Registrar<ParticleType<*>> by Registrar(MOD_ID, Registries.PARTICLE_TYPE) {

    val MothFuzz: SimpleParticleType by simpleParticleType("moth_fuzz") {
        factory { sprites -> ParticleProvider { _, clientLevel, x, y, z, _, _, _ -> MothFuzzParticle(clientLevel, x, y, z, sprites) } }
    }

    val DashTrail: ParticleType<DashTrailParticleOptions> by particleType(
        "dash_trail",
        overrideLimiter = true
    ) {
        factory(::DashTrailParticle)
    }

    val FallingStar: SimpleParticleType by simpleParticleType("falling_star") {
        factory { sprites -> ParticleProvider { _, level, x, y, z ,xs, ys, zs -> FallingStarParticle(level, x, y, z, xs, ys, zs, sprites) } }
    }

    val Dreaming: SimpleParticleType by simpleParticleType("dreaming") {
        factory { sprites -> ParticleProvider { _, level, x, y, z ,xs, ys, zs -> DreamingParticle(level, x, y, z, xs, ys, zs, sprites) } }
    }

    val ColoredCloud: ParticleType<ColoredCloudParticleOptions> by particleType("colored_cloud") {
        factory { sprites -> ParticleProvider { options, level, x, y, z, xs, ys, zs -> ColoredEmissiveCloudParticle(sprites, options, level, x, y, z, xs, ys, zs) } }
    }
    // TODO: Implement dream block ripples from walking on punching
//    val DreamBlockRipple: SimpleParticleType by particleType("dream_ripple") {
//        overrideLimiter = true
//    }


}