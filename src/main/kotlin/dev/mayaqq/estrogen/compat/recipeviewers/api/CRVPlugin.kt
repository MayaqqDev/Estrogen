package dev.mayaqq.estrogen.compat.recipeviewers.api

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

interface CRVPlugin {
    val removedItems: List<ItemStack>
    val removedRecipes: List<ResourceLocation>
    val recipes: List<ViewerInfo<*, *>>

    val pseudoRecipes: List<PseudoRecipeHolder<*>>
}