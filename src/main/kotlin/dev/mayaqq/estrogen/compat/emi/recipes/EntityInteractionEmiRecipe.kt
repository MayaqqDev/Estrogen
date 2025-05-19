package dev.mayaqq.estrogen.compat.emi.recipes

import dev.emi.emi.api.recipe.BasicEmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.SlotWidget
import dev.emi.emi.api.widget.WidgetHolder
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.emi.addTexture
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.getSpawnEggs
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.crafting.Ingredient


class EntityInteractionEmiRecipe(category: EmiRecipeCategory, val recipe: EntityInteractionRecipe) : BasicEmiRecipe(category, recipe.recipeId, 177, 70) {
    init {
        this.inputs.add(EmiIngredient.of(recipe.ingredients[0]))
        this.outputs.add(EmiStack.of(recipe.result))

    }
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addTexture(RecipeTextures.JEI_DOWN_ARROW, 62, 47)
        widgets.addTexture(RecipeTextures.JEI_SHADOW, 74, 10)

        val matchingStacks: Array<ItemStack> = recipe.entity.getSpawnEggs().toTypedArray()

        val eggs: SlotWidget = widgets.addSlot(EmiIngredient.of(Ingredient.of(*matchingStacks)), 27, 38)

        widgets.addSlot(inputs[0], 51, 5)

        widgets.addSlot(outputs[0], 132, 38).recipeContext(this)

        widgets.addDrawable(0, 0, 0, 0) { matrices, mouseX, mouseY, delta ->
            val item = (System.currentTimeMillis() / 1000 % eggs.stack.emiStacks.size).toInt()
            val current: EmiIngredient = eggs.stack.emiStacks[item]
            val stack = current.emiStacks[0].itemStack
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
}