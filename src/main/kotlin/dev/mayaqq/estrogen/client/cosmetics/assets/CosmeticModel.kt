package dev.mayaqq.estrogen.client.cosmetics.assets

import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import dev.mayaqq.cynosure.client.models.ModelData
import dev.mayaqq.cynosure.client.models.animations.Animatable
import dev.mayaqq.cynosure.client.models.bake
import dev.mayaqq.cynosure.client.models.baked.CustomBakedModel
import dev.mayaqq.estrogen.client.cosmetics.CACHE
import dev.mayaqq.estrogen.client.cosmetics.CosmeticAPI
import org.joml.Vector3fc
import java.io.File
import java.io.Reader
import kotlin.jvm.optionals.getOrNull


class CosmeticModel(url: String) : DownloadAsset<ModelData, CustomBakedModel>(CACHE.resolve("models"), url) {

    override fun ModelData.onLoad() {
        try {
            result = this.bake().getOrThrow()
        } catch (e: Exception) {
            CosmeticAPI.error("Failed to bake cosmetic model: {}", url, e)
        }
    }

    override fun read(reader: () -> Reader): ModelData? {
        try {
            reader.invoke().use { reader ->
                return ModelData.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(reader))
                    .resultOrPartial(CosmeticAPI::error).getOrNull()
            }
        } catch (ex: Exception) {
            CosmeticAPI.error("Failed to load cosmetic model: {}", url, ex)
            return null
        }
    }

    companion object {
        val CODEC: Codec<CosmeticModel> = Codec.STRING.xmap(::CosmeticModel, CosmeticModel::url)
    }
}