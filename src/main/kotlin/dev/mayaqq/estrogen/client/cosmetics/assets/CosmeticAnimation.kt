package dev.mayaqq.estrogen.client.cosmetics.assets

import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import dev.mayaqq.cynosure.client.models.animations.AnimationDefinition
import dev.mayaqq.estrogen.client.cosmetics.CACHE
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import java.io.File
import java.io.Reader
import kotlin.jvm.optionals.getOrNull


class CosmeticAnimation(url: String) : DownloadAsset<AnimationDefinition, AnimationDefinition>(
    CACHE.resolve("animations"),
    url
) {

    override fun AnimationDefinition.onLoad() {
        result = this
    }

    override fun read(reader: () -> Reader): AnimationDefinition? {
        try {
            reader.invoke().use { reader ->
                return AnimationDefinition.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                    .resultOrPartial{
                        CosmeticAPI.error("Failed to read cosmetic from url [{}]: {}", url, it)
                    }.getOrNull()
            }
        } catch (e: Exception) {
            CosmeticAPI.error("Failed to load cosmetic from url [{}]", url, e)
            return null
        }
    }

    companion object {
        val CODEC: Codec<CosmeticAnimation> = Codec.STRING.xmap(::CosmeticAnimation, CosmeticAnimation::url)

        fun fromLocalFile(file: File): CosmeticAnimation {
            require(file.isFile()) { "File is not a file" }
            val animation = CosmeticAnimation("")
            animation.load(file, "")
            return animation
        }
    }
}