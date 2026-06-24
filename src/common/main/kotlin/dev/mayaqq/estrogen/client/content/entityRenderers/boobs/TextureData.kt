package dev.mayaqq.estrogen.client.content.entityRenderers.boobs

import net.minecraft.resources.ResourceLocation

data class TextureData(
    val location: ResourceLocation,
    val u: Float,
    val v: Float,
    val leftU: Float,
    val leftV: Float,
    val rightU: Float,
    val rightV: Float,
    val textureWidth: Float,
    val textureHeight: Float
)