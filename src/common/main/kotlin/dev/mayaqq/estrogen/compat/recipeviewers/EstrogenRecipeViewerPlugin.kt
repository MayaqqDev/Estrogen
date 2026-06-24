package dev.mayaqq.estrogen.compat.recipeviewers

import dev.mayaqq.estrogen.compat.recipeviewers.api.*
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.*
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.blocks.RichCauldronInteraction
import dev.mayaqq.estrogen.id
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.AbstractCauldronBlock

@CRVPluginEntrypoint
object EstrogenRecipeViewerPlugin : CRVPlugin {
    override val removedItems: List<ItemStack>
        get() = listOf(EstrogenBlocks.ColonThreeBlock.asItem().defaultInstance)
    override val removedRecipes: List<ResourceLocation>
        get() = listOf(id("colon_three_manual_only"))
    override val recipes: List<ViewerInfo<*, *>> = listOf(
        EntityInteractionCRVRecipe,
        LiquidEstrogenCauldronCRVRecipe,
        SpongingCRVRecipe
    )
    override val pseudoRecipes: List<PseudoRecipeHolder<RecipeData>> = listOf(
        PseudoRecipeHolder({
            buildList {
                BuiltInRegistries.BLOCK.forEach {
                    if (it is AbstractCauldronBlock) {
                        it.interactions.forEach { (item, interaction) ->
                            if (interaction is RichCauldronInteraction) {
                                if (interaction.enabled.invoke()) {
                                    add(CIPRData(item, interaction, it))
                                }
                            }
                        }
                    }
                }
            }
        }, { interaction -> CauldronInteractionPseudoRecipe(interaction as CIPRData) as CRVPseudoRecipe<RecipeData> },
            Items.CAULDRON.defaultInstance,
            Items.AIR.defaultInstance,
            id("cauldron_interaction"),
            177,
            70
        )
    )
}