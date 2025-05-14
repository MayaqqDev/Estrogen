package dev.mayaqq.estrogen.compat.emi

import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiStack
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.world.item.crafting.RecipeType

@EmiEntrypoint
object EmiEstrogenPlugin : EmiPlugin {
    override fun register(registry: EmiRegistry) {
        addRecipe(registry, EntityInteractionRecipe, EstrogenRecipes.ENTITY_INTERACTION)
    }

    fun addRecipe(registry: EmiRegistry, viewerInfo: RecipeViewerInfo, recipeType: RecipeType<*>) {
        val category = EmiRecipeCategory(viewerInfo.id, StackWithCatalystEmiRenderable(viewerInfo.display, viewerInfo.catalyst))
        registry.addCategory(category)
    }
}