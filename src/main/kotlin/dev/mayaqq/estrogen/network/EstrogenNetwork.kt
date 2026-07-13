package dev.mayaqq.estrogen.network

import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.network.messages.c2s.*
import dev.mayaqq.estrogen.network.messages.s2c.*
import invoke.kitty.kritter.network.api.NetworkChannel
import invoke.kitty.kritter.serialization.builtins.MinecraftSerializersModule

val EstrogenNetwork = NetworkChannel(id("main"), 1, serializers = MinecraftSerializersModule) {
    // S2C
    playS2C<ChestConfigPacket>(handler = ChestConfigPacket::handle)
    playS2C<DreamBlockSeedPacket>(handler = DreamBlockSeedPacket::handle)
    playS2C<ThighHighStylesPacket>(handler = ThighHighStylesPacket::handle)
    playS2C<FlapSyncPacket>(handler = FlapSyncPacket::handle)
    playS2C<ChestConfigRequestPacket> { ChestConfigRequestPacket.handle() }
    playS2C<CosmeticUpdatePacket>(handler = CosmeticUpdatePacket::handle)

    // C2S
    playC2S<DashPacket>(handler = DashPacket::handle)
    playC2S<SetChestConfigPacket>(handler = SetChestConfigPacket::handle)
    playC2S<SpawnHeartsPacket>(handler = SpawnHeartsPacket::handle)
    playC2S<DreamBlockRipplePacket>(handler = DreamBlockRipplePacket::handle)
    playC2S<FlapPacket>(handler = FlapPacket::handle)
    playC2S<UpdatedCosmeticPacket>(handler = UpdatedCosmeticPacket::handle)
    playC2S<DashAirtimeParticlesPacket> { _, _, player -> DashAirtimeParticlesPacket.handle(player) }
}