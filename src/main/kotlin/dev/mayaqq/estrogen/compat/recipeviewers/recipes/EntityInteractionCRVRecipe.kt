package dev.mayaqq.estrogen.compat.recipeviewers.recipes

import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVIngredient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.Role
import dev.mayaqq.estrogen.compat.recipeviewers.api.ViewerInfo
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.getSpawnEggs
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.crafting.Ingredient

class EntityInteractionCRVRecipe(recipe: EntityInteractionRecipe) : CRVRecipe<EntityInteractionRecipe>(recipe) {
    override fun init() {
        addTexture(RecipeTextures.JEI_SHADOW, 62, 47)
        addTexture(RecipeTextures.JEI_DOWN_ARROW, 74, 10)

        val matchingStacks: Array<ItemStack> = recipe.entity.getSpawnEggs()
        val eggs = addSlot(CRVIngredient.of(Ingredient.of(*matchingStacks)), 27, 38, Role.RENDER_ONLY)

        addSlot(inputs[0], 51, 5, Role.INPUT)
        addSlot(outputs[0], 132, 38, Role.OUTPUT)

        addDrawable(0, 0) { matrices, offsetX, offsetY, mouseX, mouseY, delta ->
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

    override val inputs: List<CRVIngredient> = listOf(CRVIngredient.of(recipe.ingredient))
    override val outputs: List<CRVIngredient> = listOf(CRVIngredient.of(recipe.result))
    override val catalysts: List<CRVIngredient> = listOf()

    companion object : ViewerInfo<EntityInteractionRecipe, EntityInteractionCRVRecipe>(
        EntityInteractionRecipe,
        { recipe -> EntityInteractionCRVRecipe(recipe as EntityInteractionRecipe) },
        EntityInteractionRecipe::class
    )
}