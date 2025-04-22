package dev.mayaqq.estrogen.config

import kotlinx.serialization.Serializable

@Serializable
data class ChestConfig(
    val enabled: Boolean,
    val armorEnabled: Boolean,
    val physicsEnabled: Boolean,
    val bounciness: Float,
    val damping: Float
)
