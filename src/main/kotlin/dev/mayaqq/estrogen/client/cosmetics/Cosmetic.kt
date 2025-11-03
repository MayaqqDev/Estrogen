package dev.mayaqq.estrogen.client.cosmetics

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.utils.file.GlobalStorage
import dev.mayaqq.estrogen.MOD_ID
import java.nio.file.Path


val CACHE: Path = GlobalStorage.getCache(MOD_ID).resolve("cosmetics")

data class Cosmetic(
    val id: String,
    val name: String,

) {
    companion object {
        fun codec(id: String): Codec<Cosmetic> = RecordCodecBuilder.create { it.group(
            RecordCodecBuilder.point(id),
            Codec.STRING fieldOf Cosmetic::name
        ).apply(it, ::Cosmetic) }
    }
}