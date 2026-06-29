package dev.mayaqq.estrogen.content.components

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import invoke.kitty.kritter.utils.color.Color

@JvmRecord
data class ThighHighColor(val primary: Color, val secondary: Color) {
    companion object {
        val CODEC: Codec<ThighHighColor> = RecordCodecBuilder.create { instance -> instance.group(
            Color.RGB_CODEC.fieldOf("primary").forGetter(ThighHighColor::primary),
            Color.RGB_CODEC.fieldOf("secondary").forGetter(ThighHighColor::secondary)
        ).apply(instance, ::ThighHighColor) }
    }
}