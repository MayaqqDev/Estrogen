package dev.mayaqq.estrogen.network.messages.s2c

import com.teamresourceful.resourcefulcosmetics.SignedData
import dev.mayaqq.cynosure.network.ClientNetworkContext
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.SerializablePacket
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import dev.mayaqq.estrogen.client.cosmetics.SignedDataSerializer
import kotlinx.serialization.Serializable

@SerializablePacket("cosmetic_update")
class CosmeticUpdatePacket(val data: @Serializable(SignedDataSerializer::class) SignedData) : Packet.Clientbound {
    override fun ClientNetworkContext.handle() {
        CosmeticAPI.updateCosmetic(data)
    }
}