package dev.mayaqq.estrogen.compat.recipeviewers

import dev.mayaqq.estrogen.compat.recipeviewers.recipes.EntityInteractionRvRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.recipes.SpongingRvRecipe
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.id

object GenericRecipeViewerPlugin {
    val removedFromRecipeViewers = listOf(
        EstrogenBlocks.ColonThreeBlock.asItem().defaultInstance
    )
    val removedRecipesFromRecipeViewers = listOf(
        id("colon_three")
    )

    val rvRecipes = listOf(
        EntityInteractionRvRecipe, SpongingRvRecipe
    )
}