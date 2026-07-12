package dev.mayaqq.estrogen.client.cosmetics.assets

import com.mojang.blaze3d.platform.NativeImage
import dev.mayaqq.cynosure.client.models.ModelData
import dev.mayaqq.cynosure.client.models.animations.AnimationDefinition
import dev.mayaqq.cynosure.client.models.bake
import dev.mayaqq.cynosure.client.models.baked.CustomBakedModel
import dev.mayaqq.cynosure.utils.coroutines.MinecraftClient
import invoke.kitty.kritter.utils.result.flatMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.resources.ResourceLocation

object CosmeticReaders {

    private val MODEL_DATA: CosmeticAsset.Reader<ModelData> = CosmeticAsset.Reader.Json(ModelData.CODEC)

    val TEXTURE: CosmeticAsset.Reader<ResourceLocation> = CosmeticAsset.Reader { bytes ->
        withContext(Dispatchers.MinecraftClient) {
            runCatching {
                val image = NativeImage.read(bytes)
                Minecraft.getInstance().textureManager.register("estrogen_cosmetic", DynamicTexture(image))
            }
        }
    }

    val MODEL: CosmeticAsset.Reader<CustomBakedModel> = CosmeticAsset.Reader { bytes ->
        MODEL_DATA.decode(bytes).flatMap(ModelData::bake)
    }

    val ANIMATION: CosmeticAsset.Reader<AnimationDefinition> = CosmeticAsset.Reader.Json(AnimationDefinition.CODEC)
}