package dev.mayaqq.estrogen.compat.emi

import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.render.EmiTexture
import dev.emi.emi.api.widget.SlotWidget
import dev.emi.emi.api.widget.WidgetHolder
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.emi.recipes.EntityInteractionEmiRecipe
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe

@EmiEntrypoint
object EmiEstrogenPlugin : EmiPlugin {

    val interactionCategory = EmiRecipeCategory(EntityInteractionRecipe.id, StackWithCatalystEmiRenderable(EntityInteractionRecipe))

    override fun register(registry: EmiRegistry) {
        registry.addCategory(interactionCategory)
        registry.recipeManager.getAllRecipesFor(EstrogenRecipes.ENTITY_INTERACTION).forEach { recipe ->
            registry.addRecipe(EntityInteractionEmiRecipe(interactionCategory, recipe))
        }
    }
}

fun WidgetHolder.addTexture(texture: RecipeTextures, x: Int, y: Int) {
    this.addTexture(EmiTexture(texture.textureLocation, texture.startX, texture.startY, texture.width, texture.height), x, y)
}

fun SlotWidget.withBackground(texture: RecipeTextures): SlotWidget = this.customBackground(texture.textureLocation, texture.startX, texture.startY, texture.width, texture.height)