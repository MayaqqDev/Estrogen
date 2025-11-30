package dev.mayaqq.estrogen.network.messages.c2s

import com.teamresourceful.resourcefulcosmetics.SignedData
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.SerializablePacket
import dev.mayaqq.cynosure.network.ServerNetworkContext
import dev.mayaqq.estrogen.client.cosmetics.SignedDataSerializer
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.s2c.CosmeticUpdatePacket
import kotlinx.serialization.Serializable

@SerializablePacket("updated_cosmetic")
class UpdatedCosmeticPacket(val data: @Serializable(SignedDataSerializer::class) SignedData) : Packet.Serverbound {

    override fun ServerNetworkContext.handle() {
        EstrogenNetwork.broadcast(CosmeticUpdatePacket(data), server)
    }
}