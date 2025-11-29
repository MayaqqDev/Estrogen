package dev.mayaqq.estrogen.config.types

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.c2s.SetChestConfigPacket
import kotlinx.serialization.Serializable

@Serializable
data class ChestConfig(
    val enabled: Boolean,
    val armorEnabled: Boolean,
    val physicsEnabled: Boolean,
    val bounciness: Double,
    val damping: Float
) {
    companion object {
        @JvmStatic
        val current get() = ChestConfig(
                EstrogenClientConfig.ChestFeature.enabled,
                EstrogenClientConfig.ChestFeature.armor,
                EstrogenClientConfig.ChestFeature.physics,
                EstrogenClientConfig.ChestFeature.bounciness,
                EstrogenClientConfig.ChestFeature.damping
            )

        @JvmStatic
        fun sync() {
            if (McClient.connection != null) EstrogenNetwork.sendToServer(SetChestConfigPacket(current))
        }
    }
}

