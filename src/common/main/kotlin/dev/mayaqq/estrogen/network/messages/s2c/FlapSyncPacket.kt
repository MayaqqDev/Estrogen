package dev.mayaqq.estrogen.network.messages.s2c

import dev.mayaqq.cynosure.network.ClientNetworkContext
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.SerializablePacket
import dev.mayaqq.estrogen.injection.flap
import net.minecraft.client.Minecraft
import java.util.*

@SerializablePacket("flap_sync")
data class FlapSyncPacket(val flaps: Int, val player: String) : Packet.Clientbound {
    override fun ClientNetworkContext.handle() = execute {
        val level = Minecraft.getInstance().level
        val player = level?.getPlayerByUUID(UUID.fromString(player)) ?: return@execute

        player.flap()
    }
}