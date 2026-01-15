package dev.mayaqq.estrogen.compat.recipeviewers

import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPlugin
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPluginEntrypoint
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPseudoRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.PseudoRecipeHolder
import dev.mayaqq.estrogen.compat.recipeviewers.api.RecipeData
import dev.mayaqq.estrogen.compat.recipeviewers.api.ViewerInfo
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.CIPRData
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.CauldronInteractionPseudoRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.EntityInteractionCRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.LiquidEstrogenCauldronCRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.SpongingCRVRecipe
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.blocks.RichCauldronInteraction
import dev.mayaqq.estrogen.id
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
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
                                add(CIPRData(item, interaction, it))
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