package dev.mayaqq.estrogen.network.messages.s2c

import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture
import kotlinx.serialization.Serializable

@Serializable
data class DreamBlockSeedPacket(val seed: Long) {
    fun handle() {
        DynamicDreamTexture.changeSeed(seed)
    }
}
