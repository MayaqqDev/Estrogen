package dev.mayaqq.estrogen.compat.emi.recipes

import dev.emi.emi.api.recipe.BasicEmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.SlotWidget
import dev.emi.emi.api.widget.WidgetHolder
import dev.mayaqq.cynosure.utils.isLeft
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.emi.addTexture
import dev.mayaqq.estrogen.compat.emi.withBackground
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.SpongingRecipe
import dev.mayaqq.estrogen.content.recipes.getSpawnEggs
import dev.mayaqq.estrogen.mixin.client.accessor.LiquidBlockAccessor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.LiquidBlock


class SpongingEmiRecipe(category: EmiRecipeCategory, val recipe: SpongingRecipe) : BasicEmiRecipe(category, recipe.recipeId, SpongingRecipe.width, SpongingRecipe.height) {
    init {
        val input = this.recipe.input
        val output = this.recipe.output
        if (input.isLeft) {
            val fluidBlock = input.left!!
            this.inputs.add(EmiStack.of((fluidBlock as LiquidBlockAccessor).fluid()))
        } else {
            this.inputs.add(EmiIngredient.of(input.right!!))
        }
        this.outputs.add(EmiStack.of(BuiltInRegistries.FLUID.get(output)))
    }

    //TODO: Make this draw correctly and nicely with a SpongeBlock as well shown :pleading_face:
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addTexture(RecipeTextures.JEI_SHADOW, 62, 47)
        widgets.addTexture(RecipeTextures.JEI_DOWN_ARROW, 74, 10)

        widgets.addSlot(inputs[0], 51, 5).withBackground(RecipeTextures.JEI_SLOT)
        widgets.addSlot(outputs[0], 132, 38).recipeContext(this).withBackground(RecipeTextures.JEI_SLOT)
    }
}