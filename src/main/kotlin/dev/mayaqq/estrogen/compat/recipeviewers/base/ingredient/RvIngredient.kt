package dev.mayaqq.estrogen.compat.recipeviewers.base.ingredient

import net.minecraft.tags.TagKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.material.Fluid

class RvIngredient(
    val size: Int = -1,
    val ingredient: Ingredient? = null,
    val items: Array<ItemStack> = arrayOf(),
    val item: ItemStack? = null,
    val fluid: Fluid? = null,
    val fluidTag: TagKey<Fluid>? = null,
) {

    companion object {
        fun of(ingredient: Ingredient) = RvIngredient(
            size = ingredient.items.size,
            ingredient = ingredient,
            items = ingredient.items
        )
        fun of(item: ItemStack) = RvIngredient(
            size = 1,
            items = arrayOf(item),
            item = item
        )
        fun of(fluid: Fluid) = RvIngredient(
            fluid = fluid
        )
        fun of(fluidTag: TagKey<Fluid>) = RvIngredient(
            fluidTag = fluidTag
        )
    }
}