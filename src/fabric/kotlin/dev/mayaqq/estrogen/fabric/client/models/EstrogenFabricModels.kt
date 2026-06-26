package dev.mayaqq.estrogen.fabric.client.models

import dev.mayaqq.cynosure.utils.Couple
import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_LOCATION
import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_TEXTURES
import dev.mayaqq.estrogen.client.THIGH_HIGH_MODELS_DIRECTORY
import dev.mayaqq.estrogen.client.content.block.ClientDreamBlock
import dev.mayaqq.estrogen.client.content.models.ThighHighsItemModel
import dev.mayaqq.estrogen.utils.resources.listResourceIds
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

private typealias Preparations = Couple<Collection<ResourceLocation>>

object EstrogenFabricModels : PreparableModelLoadingPlugin<Preparations>, PreparableModelLoadingPlugin.DataLoader<Preparations> {

    override fun load(manager: ResourceManager, executor: Executor): CompletableFuture<Preparations> {
        val modelsFuture: CompletableFuture<Collection<ResourceLocation>> = CompletableFuture.supplyAsync(
            fun()= manager.listResourceIds(THIGH_HIGH_MODELS_DIRECTORY, "models", ".json").first,
            executor
        )

        val texturesFuture: CompletableFuture<Collection<ResourceLocation>> = CompletableFuture.supplyAsync(
            fun() = manager.listResourceIds(THIGH_HIGH_ITEM_TEXTURES, "textures", ".png").first,
            executor
        )

        return modelsFuture.thenCombineAsync(texturesFuture, Collection<ResourceLocation>::to, executor)
    }

    override fun onInitializeModelLoader(preparations: Preparations, context: ModelLoadingPlugin.Context) {
        val (models, textures) = preparations
        context.addModels(models)
        context.modifyModelOnLoad().register(ModelModifier.WRAP_LAST_PHASE) { model, ctx ->
            if (ctx.topLevelId() == THIGH_HIGH_ITEM_LOCATION) ThighHighsItemModel(model, textures, ::FabricThighHighsModel)
            else model
        }

        context.modifyModelAfterBake().register { model, ctx ->
            if (ctx.resourceId() == ClientDreamBlock.DORMANT_MODEL)
                FabricConnectedModel(model!!, ctx.textureGetter().apply(ClientDreamBlock.DORMANT_CONNECTED_TEXTURE))
            else model
        }
    }

}