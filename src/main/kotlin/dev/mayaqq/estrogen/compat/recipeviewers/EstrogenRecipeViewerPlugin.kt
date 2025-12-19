package dev.mayaqq.estrogen.compat.recipeviewers

import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPlugin
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPluginEntrypoint
import dev.mayaqq.estrogen.compat.recipeviewers.api.ViewerInfo
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.EntityInteractionCRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.LiquidEstrogenCauldronCRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.SpongingCRVRecipe
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.id
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

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
}