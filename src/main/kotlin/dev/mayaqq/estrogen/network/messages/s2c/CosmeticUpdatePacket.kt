package dev.mayaqq.estrogen.network.messages.s2c

import com.teamresourceful.resourcefulcosmetics.SignedData
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import dev.mayaqq.estrogen.client.cosmetics.SignedDataSerializer
import kotlinx.serialization.Serializable

@Serializable
class CosmeticUpdatePacket(val data: @Serializable(SignedDataSerializer::class) SignedData) {
    fun handle() {
        CosmeticAPI.updateCosmetic(data)
    }
}