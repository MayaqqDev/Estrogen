package dev.mayaqq.estrogen.network.messages.s2c

import dev.mayaqq.estrogen.config.types.ChestConfig

class ChestConfigRequestPacket {
    fun handle() {
        ChestConfig.sync()
    }
}
