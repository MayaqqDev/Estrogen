package dev.mayaqq.estrogen.network.messages.c2s

import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.SerializablePacket
import dev.mayaqq.cynosure.network.ServerNetworkContext
import dev.mayaqq.estrogen.config.types.ChestConfig
import dev.mayaqq.estrogen.injection.chestConfig

@SerializablePacket("set_chest_config")
data class SetChestConfigPacket(val config: ChestConfig) : Packet.Serverbound {
    override fun ServerNetworkContext.handle() = execute {
        sender.chestConfig = config
    }
}