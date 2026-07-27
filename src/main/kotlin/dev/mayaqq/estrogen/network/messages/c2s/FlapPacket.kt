package dev.mayaqq.estrogen.network.messages.c2s

import dev.mayaqq.estrogen.injection.flap
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.s2c.FlapSyncPacket
import kotlinx.serialization.Serializable
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import kotlin.uuid.toKotlinUuid

@Serializable @JvmRecord
data class FlapPacket(val flap: Int) {
    fun handle(server: MinecraftServer, sender: ServerPlayer) {
        sender.flap()
        sender.level().playSound(null, sender, SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0F, 1.4F)
        server.playerList.players.forEach { player ->
            EstrogenNetwork.sendToPlayer(player, FlapSyncPacket(flap, sender.uuid.toKotlinUuid()))
        }
    }
}