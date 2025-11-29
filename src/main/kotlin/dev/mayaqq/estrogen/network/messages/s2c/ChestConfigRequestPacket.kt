package dev.mayaqq.estrogen.network.messages.s2c

import dev.mayaqq.cynosure.network.ClientNetworkContext
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.SerializablePacket
import dev.mayaqq.estrogen.config.types.ChestConfig

@SerializablePacket("chest_request")
class ChestConfigRequestPacket : Packet.Clientbound {
    override fun ClientNetworkContext.handle() {
        ChestConfig.sync()
    }
}
