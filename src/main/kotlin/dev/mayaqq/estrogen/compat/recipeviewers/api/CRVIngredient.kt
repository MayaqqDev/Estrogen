package dev.mayaqq.estrogen.compat.recipeviewers.api

import net.minecraft.tags.TagKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.material.Fluid

class CRVIngredient(
    val size: Int = -1,
    val ingredient: Ingredient? = null,
    val items: Array<ItemStack> = arrayOf(),
    val item: ItemStack? = null,
    val fluid: Fluid? = null,
    val fluidTag: TagKey<Fluid>? = null,
) {

    companion object {
        fun of(ingredient: Ingredient) = CRVIngredient(
            size = ingredient.items.size,
            ingredient = ingredient,
            items = ingredient.items
        )
        fun of(item: ItemStack) = CRVIngredient(
            size = 1,
            items = arrayOf(item),
            item = item
        )
        fun of(fluid: Fluid) = CRVIngredient(
            fluid = fluid
        )
        fun of(fluidTag: TagKey<Fluid>) = CRVIngredient(
            fluidTag = fluidTag
        )
    }
}