package dev.mayaqq.estrogen.content.recipes

import dev.mayaqq.estrogen.content.EstrogenRecipes
import net.minecraft.core.NonNullList
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*

class CreativelessShapedRecipe(
    id: ResourceLocation,
    group: String,
    category: CraftingBookCategory,
    width: Int,
    height: Int,
    recipeItems: NonNullList<Ingredient>,
    result: ItemStack,
    showNotification: Boolean) :
    ShapedRecipe(id, group, category, width, height, recipeItems, result, showNotification) {

    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.CREATIVELESS_SHAPED_RECIPE_SERIALIZER
    override fun getType(): RecipeType<*> = EstrogenRecipes.CREATIVELESS_SHAPED_RECIPE
}