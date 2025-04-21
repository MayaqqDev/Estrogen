package dev.mayaqq.estrogen.network

import dev.mayaqq.cynosure.network.NetworkChannel
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.network.messages.c2s.DashPacket
import dev.mayaqq.estrogen.network.messages.c2s.FinishedLoadingPacket
import dev.mayaqq.estrogen.network.messages.c2s.SetChestConfigPacket
import dev.mayaqq.estrogen.network.messages.c2s.SpawnHeartsPacket
import dev.mayaqq.estrogen.network.messages.s2c.ChestConfigPacket
import dev.mayaqq.estrogen.network.messages.s2c.DreamBlockSeedPacket
import dev.mayaqq.estrogen.network.messages.s2c.ThighHighStylesPacket

val EstrogenNetwork = NetworkChannel(id("main"), 1) {
    // S2C
    clientbound<ChestConfigPacket>(ChestConfigPacket.CODEC)
    clientbound<DreamBlockSeedPacket>()
    clientbound<ThighHighStylesPacket>(ThighHighStylesPacket.CODEC)

    // C2S
    serverbound<DashPacket>()
    serverbound<FinishedLoadingPacket>()
    serverbound<SetChestConfigPacket>()
    serverbound<SpawnHeartsPacket>(SpawnHeartsPacket.CODEC)
}