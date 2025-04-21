package dev.mayaqq.estrogen.network.messages.c2s

import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.SerializablePacket
import dev.mayaqq.cynosure.network.ServerNetworkContext

@SerializablePacket("dash")
data class DashPacket(val isInitial: Boolean, val dashLevel: Int) : Packet.Serverbound {
    override fun ServerNetworkContext.handle() {
        TODO("Not yet implemented")
    }
}