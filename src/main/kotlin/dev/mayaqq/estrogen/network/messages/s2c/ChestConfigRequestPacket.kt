package dev.mayaqq.estrogen.network.messages.s2c

import dev.mayaqq.estrogen.config.types.ChestConfig
import kotlinx.serialization.Serializable

@Serializable
class ChestConfigRequestPacket {
    fun handle() {
        ChestConfig.sync()
    }
}
