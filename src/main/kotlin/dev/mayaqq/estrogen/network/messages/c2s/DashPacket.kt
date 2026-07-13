package dev.mayaqq.estrogen.network.messages.c2s

import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.EstrogenSounds
import dev.mayaqq.estrogen.content.particles.ColoredCloudParticleOptions
import dev.mayaqq.estrogen.content.particles.DashTrailParticleOptions
import dev.mayaqq.estrogen.features.dash.CommonDash
import dev.mayaqq.estrogen.utils.EstrogenColors
import invoke.kitty.kritter.registry.api.entry.holder
import kotlinx.serialization.Serializable
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import kotlin.uuid.toKotlinUuid

@Serializable @JvmRecord
data class DashPacket(val isInitial: Boolean, val dashLevel: Int) {
    fun handle(server: MinecraftServer, sender: ServerPlayer) {
        if (sender.hasEffect(EstrogenEffects.Estrogen.holder)) {
            val level = sender.level() as ServerLevel

            val dashColor = EstrogenColors.getDashColor(dashLevel, true)

            // made them nicer hope u like maya c:
            level.sendParticles(
                ColoredCloudParticleOptions(dashColor, true),
                sender.x, sender.y + 0.5, sender.z, 3, 0.2, 0.7, 0.2, 0.18
            )

            if (isInitial) {
                // Set dashing and play dash sound if this is the initial packet
                CommonDash.setDashing(sender.uuid)
                level.playSound(null, sender, EstrogenSounds.DASH.get(), SoundSource.PLAYERS, 1.0f, 1.0f)
            } else {
                // Otherwise spawn trail
                level.sendParticles(
                    DashTrailParticleOptions(sender.uuid.toKotlinUuid(), dashColor),
                    sender.xOld, sender.yOld, sender.zOld,
                    0, 0.0, 0.0, 0.0, 0.0
                )
            }
        }
    }
}