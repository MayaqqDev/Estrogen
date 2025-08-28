package dev.mayaqq.estrogen.fabric.client.models

import dev.mayaqq.cynosure.utils.Couple
import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_LOCATION
import dev.mayaqq.estrogen.client.THIGH_HIGH_ITEM_TEXTURES
import dev.mayaqq.estrogen.client.THIGH_HIGH_MODELS_DIRECTORY
import dev.mayaqq.estrogen.client.content.block.ClientDreamBlock
import dev.mayaqq.estrogen.client.content.models.ThighHighsItemModel
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.utils.resources.listResourceIds
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.client.resources.model.ModelBaker
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.function.Supplier

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
            if (ctx.id() == THIGH_HIGH_ITEM_LOCATION) ThighHighsItemModel(model, textures, ::FabricThighHighsModel)
            else model
        }

        context.modifyModelAfterBake().register { model, ctx ->
            if (ctx.id() == ClientDreamBlock.DORMANT_MODEL)
                FabricDreamBlockModel(model!!, ctx.textureGetter().apply(ClientDreamBlock.DORMANT_CONNECTED_TEXTURE))
            else model
        }
    }

    private class FabricThighHighsModel(default: BakedModel, val styleModels: Map<ResourceLocation, BakedModel>) : ForwardingBakedModel() {

        init {
            wrapped = default
        }

        override fun isVanillaAdapter(): Boolean = false

        override fun emitItemQuads(
            stack: ItemStack,
            randomSupplier: Supplier<RandomSource>,
            context: RenderContext
        ) {
            val model = EstrogenItems.ThighHighs.getStyle(stack)?.let(styleModels::get) ?: wrapped
            (model as FabricBakedModel).emitItemQuads(stack, randomSupplier, context)
        }
    }

}