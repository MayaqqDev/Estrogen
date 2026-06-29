package dev.mayaqq.estrogen.content.components

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.resources.ResourceLocation

@JvmRecord
data class ThighHighStyle(val style: ResourceLocation) {
    companion object {
        val CODEC: Codec<ThighHighStyle> = RecordCodecBuilder.create { instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("style").forGetter(ThighHighStyle::style),
        ).apply(instance, ::ThighHighStyle) }
    }
}
