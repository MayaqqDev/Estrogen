package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.recipes.codecSerializer
import dev.mayaqq.cynosure.recipes.recipeType
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.recipes.DreamCatcherDyeRecipe
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.SpongingRecipe
import dev.mayaqq.estrogen.content.recipes.ThighHighDyeRecipe
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry

object EstrogenRecipes : Registrar<RecipeType<*>> by Estrogen..Registries.RECIPE_TYPE {
    val ENTITY_INTERACTION by recipeType<EntityInteractionRecipe>("entity_interaction")
    val SPONGING by recipeType<SpongingRecipe>("sponging")

    object Serializers : Registrar<RecipeSerializer<*>> by sibling(Registries.RECIPE_SERIALIZER) {
        val ENTITY_INTERACTION_SERIALIZER by codecSerializer("entity_interaction", EntityInteractionRecipe::codec, EntityInteractionRecipe::netcodec) {}
        val SPONGING by codecSerializer("sponging", SpongingRecipe::codec, SpongingRecipe::netcodec) {}
        val THIGH_HIGH_DYE_SERIALIZER by entry("thigh_high_dye", { SimpleCraftingRecipeSerializer(::ThighHighDyeRecipe) })
        val DREAMCATCHER_DYE_SERIALIZER by entry("dreamcatcher_dye", { SimpleCraftingRecipeSerializer(::DreamCatcherDyeRecipe) })
    }
}