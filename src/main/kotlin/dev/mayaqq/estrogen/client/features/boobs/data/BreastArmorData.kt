@file:UseSerializers(ResourceLocationSerializer::class, PairAsArraySerializer::class)
package dev.mayaqq.estrogen.client.features.boobs.data

import dev.mayaqq.estrogen.client.content.entityRenderers.boobs.TextureData
import invoke.kitty.kritter.serialization.PairAsArraySerializer
import invoke.kitty.kritter.serialization.builtins.ResourceLocationSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.minecraft.resources.ResourceLocation

@Serializable
data class BreastArmorData(
    val texture: ResourceLocation?,
    @SerialName("texture_overlay")
    val overlayLocation: ResourceLocation?,
    val uv: Pair<Float, Float>,
    @SerialName("left_uv")
    val leftUV: Pair<Float, Float>,
    @SerialName("right_uv")
    val rightUV: Pair<Float, Float>,
    val size: Pair<Float, Float>
) {

    fun toTextureData(overlay: Boolean): TextureData? {
        return if (overlay) {
            this.toTextureData(overlayLocation)
        } else this.toTextureData(texture)
    }

    fun toTextureData(textureLocation: ResourceLocation?): TextureData? {
        return if (textureLocation == null) null else {
            TextureData(textureLocation, uv.first, uv.second, leftUV.first, leftUV.second, rightUV.first, rightUV.second, size.first, size.second)
        }
    }
}