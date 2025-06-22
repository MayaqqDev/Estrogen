package dev.mayaqq.estrogen.compat.rei

import dev.mayaqq.estrogen.compat.rei.recipes.EntityInteractionReiRecipe
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.id
import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes

object ReiEstrogenPlugin : REIClientPlugin {
    override fun getPluginProviderName(): String = id("rei_client").toString()

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(EntityInteractionReiRecipe.Category(EntityInteractionRecipe))
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        registry.add(EntityInteractionReiRecipe)
        registry.registerRecipeFiller(EntityInteractionRecipe::class.java, EstrogenRecipes.ENTITY_INTERACTION) { recipe ->
            EntityInteractionReiRecipe(recipe, recipe.entity)
        }
    }

    @Suppress("UnstableApiUsage")
    override fun registerBasicEntryFiltering(rule: BasicFilteringRule<*>) {
        rule.hide(EntryStack.of(VanillaEntryTypes.ITEM, EstrogenBlocks.ColonThreeBlock.asItem().defaultInstance))
    }
}