package dev.mayaqq.estrogen.client.content.models

import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_LOCATION
import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_TEXTURES
import dev.mayaqq.estrogen.client.THIGH_HIGH_MODELS_DIRECTORY
import dev.mayaqq.estrogen.utils.resources.listResourceIds
import invoke.kitty.kritter.client.model.ModelLoadingContext
import invoke.kitty.kritter.client.model.PreparableModelLoadingPlugin
import invoke.kitty.kritter.utils.Couple
import invoke.kitty.kritter.utils.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.client.resources.model.Material
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager

private typealias Preparations = Couple<Collection<ResourceLocation>>

object EstrogenModels : PreparableModelLoadingPlugin<Preparations> {

    private val CT_MODELS = mutableMapOf<ResourceLocation, ResourceLocation>()

    fun registerConnected(id: ResourceLocation, texture: ResourceLocation) {
        if (CT_MODELS.putIfAbsent(id, texture) != null)
            throw IllegalArgumentException("Model '$id' already registered")
    }

    override suspend fun prepare(manager: ResourceManager): Preparations = coroutineScope {
        val models = async { manager.listResourceIds(THIGH_HIGH_MODELS_DIRECTORY, "models", ".json").first }
        val itemTextures = async { manager.listResourceIds(THIGH_HIGH_ITEM_TEXTURES, "textures", ".png").first }
        models.await() to itemTextures.await()
    }

    override fun ModelLoadingContext.onInitializePlugin(data: Preparations) {
        val (models, textures) = data
        +models

        modifyModelsOnLoad.subscribe {
            if (id.left == THIGH_HIGH_ITEM_LOCATION) ThighHighsItemModel(it, textures) else it
        }

        modifyModelsAfterBake.subscribe {
            if (it != null && id is Either.Right && id.right in CT_MODELS)
                ConnectedModel(it, spriteGetter.apply(Material(TextureAtlas.LOCATION_BLOCKS, CT_MODELS[id.right]!!)))
            else
                null
        }
    }


}