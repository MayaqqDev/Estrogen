package dev.mayaqq.estrogen.network

import dev.mayaqq.cynosure.network.NetworkChannel
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.network.messages.c2s.*
import dev.mayaqq.estrogen.network.messages.s2c.ChestConfigPacket
import dev.mayaqq.estrogen.network.messages.s2c.DreamBlockSeedPacket
import dev.mayaqq.estrogen.network.messages.s2c.FlapSyncPacket
import dev.mayaqq.estrogen.network.messages.s2c.ThighHighStylesPacket

val EstrogenNetwork = NetworkChannel(id("main"), 1) {
    // S2C
    clientbound<ChestConfigPacket>(ChestConfigPacket.CODEC)
    clientbound<DreamBlockSeedPacket>()
    clientbound<ThighHighStylesPacket>(ThighHighStylesPacket.CODEC)
    clientbound<FlapSyncPacket>()

    // C2S
    serverbound<DashPacket>()
    serverbound<SetChestConfigPacket>()
    serverbound<SpawnHeartsPacket>(SpawnHeartsPacket.CODEC)
    serverbound(DreamBlockRipplePacket.CODEC)
    serverbound<FlapPacket>()
}