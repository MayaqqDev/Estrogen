package dev.mayaqq.estrogen.compat.jei

import dev.mayaqq.estrogen.compat.jei.recipes.EntityInteractionJeiRecipe
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.id
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation

@JeiPlugin
object JeiEstrogenPlugin : IModPlugin {
    override fun getPluginUid(): ResourceLocation = id("jei_plugin")

    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        registry.addRecipeCategories(EntityInteractionJeiRecipe)
    }

    override fun registerRecipes(registry: IRecipeRegistration) {
        registry.addRecipes(EntityInteractionJeiRecipe.recipeType, Minecraft.getInstance().level?.recipeManager?.getAllRecipesFor(EstrogenRecipes.ENTITY_INTERACTION)?: return)
    }
}