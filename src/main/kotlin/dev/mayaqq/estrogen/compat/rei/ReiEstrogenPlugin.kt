package dev.mayaqq.estrogen.compat.rei

import dev.mayaqq.estrogen.compat.rei.recipes.EntityInteractionReiRecipe
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.id
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry

object ReiEstrogenPlugin : REIClientPlugin {
    override fun getPluginProviderName(): String = id("rei_client").toString()

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(EntityInteractionReiRecipe.Category(EntityInteractionRecipe))
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        registry.add(EntityInteractionReiRecipe)
        registry.registerRecipeFiller(EntityInteractionRecipe::class.java, EstrogenRecipes.ENTITY_INTERACTION) { recipe ->
            return@registerRecipeFiller EntityInteractionReiRecipe(recipe, recipe.entity)
        }
    }
}