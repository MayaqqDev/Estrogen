package dev.mayaqq.estrogen.network.messages.c2s

import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer


data object DashAirtimeParticlesPacket {

    fun handle(player: ServerPlayer) {
        (player.level() as ServerLevel).sendParticles(
            ParticleTypes.CLOUD,
            player.x, player.y, player.z, 1, 0.1, 0.1, 0.1, 0.02
        )
    }
}