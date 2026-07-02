package dev.mayaqq.estrogen.client.content.models

import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_LOCATION
import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_TEXTURES
import dev.mayaqq.estrogen.client.THIGH_HIGH_MODELS_DIRECTORY
import dev.mayaqq.estrogen.utils.resources.listResourceIds
import invoke.kitty.kritter.client.model.ModelLoadingContext
import invoke.kitty.kritter.client.model.PreparableModelLoadingPlugin
import invoke.kitty.kritter.utils.Couple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager

private typealias Preparations = Couple<Collection<ResourceLocation>>

class EstrogenModels : PreparableModelLoadingPlugin<Preparations> {

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

    }


}