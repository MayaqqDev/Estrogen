package dev.mayaqq.estrogen.network.messages.c2s

import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.EstrogenSounds
import dev.mayaqq.estrogen.features.dash.CommonDash
import dev.mayaqq.estrogen.utils.EstrogenColors
import dev.mayaqq.estrogen.utils.holder
import kotlinx.serialization.Serializable
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource

@Serializable
data class DashPacket(val isInitial: Boolean, val dashLevel: Int) {
    fun handle(server: MinecraftServer, sender: ServerPlayer) {
        if (sender.hasEffect(EstrogenEffects.Estrogen.holder())) {
            val level = sender.level() as ServerLevel

            // Spawn particles around the player
            level.sendParticles(ParticleTypes.CLOUD, sender.x, sender.y, sender.z, 10, 0.5, 0.5, 0.5, 0.5)

            if (isInitial) {
                // Set dashing and play dash sound if this is the initial packet
                CommonDash.setDashing(sender.uuid)
                level.playSound(null, sender, EstrogenSounds.DASH.value!!, SoundSource.PLAYERS, 1.0f, 1.0f)
            } else {
                // Otherwise spawn trail
                val dashColor = EstrogenColors.getDashColor(dashLevel, true)
                /* TODO: Particles
                level.sendParticles(
                    DashTrailParticleOptions(sender.uuid, dashColor),
                    sender.xOld, sender.yOld, sender.zOld,
                    0, 0.0, 0.0, 0.0, 0.0
                )
                 */
            }
        }
    }
}