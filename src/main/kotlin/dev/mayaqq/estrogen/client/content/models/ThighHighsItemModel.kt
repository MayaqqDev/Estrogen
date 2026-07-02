package dev.mayaqq.estrogen.client.content.models

import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.estrogen.Estrogen
import invoke.kitty.kritter.utils.dfu.DFUEither
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.client.renderer.block.model.ItemModelGenerator
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.InventoryMenu
import java.util.function.Function


class ThighHighsItemModel(
    private val default: UnbakedModel,
    private val styleTextures: Collection<ResourceLocation>
) : UnbakedModel {

    private companion object {
        private val MODEL_GENERATOR: ItemModelGenerator = ItemModelGenerator()

        private fun textureToStyleLocation(original: ResourceLocation): ResourceLocation {
            return identifier(original.namespace, original.path.substring("item/thigh_highs/".length))
        }
    }

    override fun getDependencies(): Collection<ResourceLocation> = default.dependencies

    override fun resolveParents(resolver: Function<ResourceLocation, UnbakedModel>) {
        default.resolveParents(resolver)
    }

    override fun bake(
        baker: ModelBaker,
        spriteGetter: Function<Material?, TextureAtlasSprite?>,
        state: ModelState
    ): BakedModel? {
        Estrogen.info("Baking {} thigh high models", styleTextures.size + 1)
        val defaultBaked = if (default is BlockModel && default.rootModel == ModelBakery.GENERATION_MARKER)
            MODEL_GENERATOR.generateBlockModel(spriteGetter, default).bake(baker, spriteGetter, state) ?: baker.bake(ModelBakery.MISSING_MODEL_LOCATION, state)!!
        else default.bake(baker, spriteGetter, state) ?: baker.bake(ModelBakery.MISSING_MODEL_LOCATION, state)!!

        val styleModels = styleTextures.associate {
            val material = Material(InventoryMenu.BLOCK_ATLAS, it)
            val model = BlockModel(
                null,
                emptyList(),
                mapOf(
                    "layer0" to DFUEither.left(material)
                ),
                false,
                BlockModel.GuiLight.FRONT,
                defaultBaked.transforms,
                emptyList()
            )

            val generated: BlockModel = MODEL_GENERATOR.generateBlockModel(spriteGetter, model)
            textureToStyleLocation(it) to generated.bake(baker, generated, spriteGetter, state, false)
        }

        return BakedThighHighsModel(defaultBaked, styleModels)
    }
}

expect class BakedThighHighsModel(default: BakedModel, styleModels: Map<ResourceLocation, BakedModel>) : BakedModel