package dev.mayaqq.estrogen.network.messages.c2s

import com.teamresourceful.resourcefulcosmetics.SignedData
import dev.mayaqq.estrogen.client.cosmetics.SignedDataSerializer
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.s2c.CosmeticUpdatePacket
import invoke.kitty.kritter.network.api.PlayerLookup
import kotlinx.serialization.Serializable
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@Serializable @JvmRecord
data class UpdatedCosmeticPacket(val data: @Serializable(SignedDataSerializer::class) SignedData) {

    fun handle(server: MinecraftServer, sender: ServerPlayer) {
        EstrogenNetwork.broadcast(PlayerLookup.all(server), CosmeticUpdatePacket(data))
    }
}