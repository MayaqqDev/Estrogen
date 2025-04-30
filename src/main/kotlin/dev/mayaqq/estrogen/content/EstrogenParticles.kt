package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.client.events.ParticleFactoryRegistrationEvent
import dev.mayaqq.cynosure.client.particles.provider
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.Environment
import dev.mayaqq.cynosure.particles.CynosureParticleType
import dev.mayaqq.cynosure.particles.particleType
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.particles.DashTrailParticle
import dev.mayaqq.estrogen.client.content.particles.MothFuzzParticle
import dev.mayaqq.estrogen.content.particles.DashTrailParticleOptions
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.core.registries.Registries
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry

@EventSubscriber(env = [Environment.CLIENT])
object EstrogenParticles : Registrar<ParticleType<*>> by Estrogen..Registries.PARTICLE_TYPE {

    val MOTH_FUZZ: SimpleParticleType by particleType("moth_fuzz")

    val DASH_TRAIL: CynosureParticleType<DashTrailParticleOptions> by particleType(
        "dash_trail",
        DashTrailParticleOptions.CODEC,
        DashTrailParticleOptions.NETWORK_CODEC
    ) {
        overrideLimiter = true
    }

    // dunno if this will cause server side issues tho it shouldn't cs of the client side subscriber thin
    // if it dooes move to estrogen client it just prettier here
    @Subscription
    fun onRegisterParticles(event: ParticleFactoryRegistrationEvent) {
        event.register(DASH_TRAIL, ::DashTrailParticle)
        event.register(MOTH_FUZZ) { sprites ->
            ParticleProvider { _, clientLevel, x, y, z, _, _, _ -> MothFuzzParticle(clientLevel, x, y, z, sprites) }
        }
    }
}