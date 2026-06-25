package dev.mayaqq.estrogen.network

import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.network.messages.c2s.*
import dev.mayaqq.estrogen.network.messages.s2c.*
import invoke.kitty.kritter.network.api.NetworkChannel

val EstrogenNetwork = NetworkChannel(id("main"), 1) {
    // S2C
    playS2C<ChestConfigPacket>(ChestConfigPacket.CODEC)
    playS2C<DreamBlockSeedPacket>()
    playS2C<ThighHighStylesPacket>(ThighHighStylesPacket.CODEC)
    playS2C<FlapSyncPacket>()
    playS2C<ChestConfigRequestPacket>()
    playS2C<CosmeticUpdatePacket>()

    // C2S
    playC2S<DashPacket>()
    playC2S<SetChestConfigPacket>()
    playC2S<SpawnHeartsPacket>(SpawnHeartsPacket.CODEC)
    playC2S(DreamBlockRipplePacket.CODEC)
    playC2S<FlapPacket>()
    playC2S<UpdatedCosmeticPacket>()
}