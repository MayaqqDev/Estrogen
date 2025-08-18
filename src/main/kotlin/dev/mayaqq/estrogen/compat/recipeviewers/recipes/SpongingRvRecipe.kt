package dev.mayaqq.estrogen.compat.recipeviewers.recipes

import dev.mayaqq.cynosure.utils.isLeft
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.base.RVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.base.Role
import dev.mayaqq.estrogen.compat.recipeviewers.base.RvRecipeData
import dev.mayaqq.estrogen.compat.recipeviewers.base.ingredient.RvIngredient
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.SpongingRecipe
import dev.mayaqq.estrogen.content.recipes.inventory.FluidData
import dev.mayaqq.estrogen.mixin.client.accessor.LiquidBlockAccessor
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Items

class SpongingRvRecipe(recipe: SpongingRecipe) : RVRecipe<SpongingRecipe>(recipe) {
    override fun init() {
        addTexture(RecipeTextures.JEI_SHADOW, 62, 47)
        addTexture(RecipeTextures.JEI_DOWN_ARROW, 74, 10)
        addSlot(inputs()[0], 51, 5, Role.INPUT)
        addSlot(outputs()[0], 132, 38, Role.OUTPUT)
    }

    override fun inputs(): List<RvIngredient> = buildList {
        if (recipe.input.isLeft) {
            val fluidBlock = recipe.input.left!!
            add(RvIngredient.of((fluidBlock as LiquidBlockAccessor).fluid()))
        } else {
            add(RvIngredient.of(recipe.input.right!!))
        }
    }

    override fun outputs(): List<RvIngredient> = listOf(RvIngredient.of(BuiltInRegistries.FLUID.get(recipe.output)))

    override fun catalysts(): List<RvIngredient> = listOf(RvIngredient.of(Items.SPONGE.defaultInstance))

    companion object : RvRecipeData<SpongingRecipe, SpongingRvRecipe>(SpongingRecipe, SpongingRvRecipe::class,
        SpongingRecipe::class)
}