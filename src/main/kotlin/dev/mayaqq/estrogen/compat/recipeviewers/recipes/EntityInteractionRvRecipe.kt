package dev.mayaqq.estrogen.compat.recipeviewers.recipes

import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.base.RVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.base.RvRecipeData
import dev.mayaqq.estrogen.compat.recipeviewers.base.ingredient.RvIngredient
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.SpongingRecipe
import dev.mayaqq.estrogen.content.recipes.getSpawnEggs
import dev.mayaqq.estrogen.content.recipes.inventory.InteractionData
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.crafting.Ingredient

class EntityInteractionRvRecipe(recipe: EntityInteractionRecipe) : RVRecipe<EntityInteractionRecipe>(recipe) {
    override fun init() {
        addTexture(RecipeTextures.JEI_SHADOW, 62, 47)
        addTexture(RecipeTextures.JEI_DOWN_ARROW, 74, 10)

        val matchingStacks: Array<ItemStack> = recipe.entity.getSpawnEggs().toTypedArray()
        val eggs = addSlot(RvIngredient.of(Ingredient.of(*matchingStacks)), 27, 38)

        addSlot(inputs()[0], 51, 5)
        addSlot(outputs()[0], 132, 38)

        addDrawable(0, 0) { matrices, mouseX, mouseY, delta ->
            val item = (System.currentTimeMillis() / 1000 % eggs.ingredient.size).toInt()
            val current = eggs.ingredient
            val stack = current.items.first()
            val entity = Minecraft.getInstance().level?.let {
                (stack.item as SpawnEggItem).getType(stack.getOrCreateTag()).create(
                    it
                )
            } as LivingEntity
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                matrices,
                88,
                55,
                20,
                -mouseX.toFloat() + 87,
                -mouseY.toFloat() + 20,
                entity
            )
        }
    }

    override fun inputs(): List<RvIngredient> = listOf(RvIngredient.of(recipe.ingredient))
    override fun outputs(): List<RvIngredient> = listOf(RvIngredient.of(recipe.result))
    override fun catalysts(): List<RvIngredient> = listOf()

    companion object : RvRecipeData<EntityInteractionRecipe, EntityInteractionRvRecipe>(EntityInteractionRecipe, EntityInteractionRvRecipe::class)

}