package dev.mayaqq.estrogen.network.messages.s2c

import dev.mayaqq.cynosure.network.ClientNetworkContext
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.SerializablePacket

@SerializablePacket("dream_block_seed")
data class DreamBlockSeedPacket(val seed: Long) : Packet.Clientbound {
    override fun ClientNetworkContext.handle() {
        TODO("Not yet implemented")
    }
}
