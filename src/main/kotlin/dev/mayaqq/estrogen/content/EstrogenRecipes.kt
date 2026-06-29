package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.recipes.codecSerializer
import dev.mayaqq.cynosure.recipes.recipeType
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.recipes.*
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer

object EstrogenRecipes : Registrar<RecipeType<*>> by Registrar(MOD_ID, Registries.RECIPE_TYPE) {
    val ENTITY_INTERACTION = recipeType<EntityInteractionRecipe>("entity_interaction")
    val SPONGING = recipeType<SpongingRecipe>("sponging")
    val LIQUID_ESTROGEN_CAULDRON = recipeType<LiquidEstrogenCauldronRecipe>("liquid_estrogen_cauldron")


    object Serializers : Registrar<RecipeSerializer<*>> by sibling(Registries.RECIPE_SERIALIZER) {
        val ENTITY_INTERACTION_SERIALIZER = codecSerializer("entity_interaction", {EntityInteractionRecipe.CODEC}, {EntityInteractionRecipe.NET_CODEC}) {}
        val SPONGING = codecSerializer("sponging", {SpongingRecipe.CODEC}, {SpongingRecipe.NETCODEC}) {}
        val THIGH_HIGH_DYE_SERIALIZER = entry("thigh_high_dye", { SimpleCraftingRecipeSerializer(::ThighHighDyeRecipe) }) {}
        val DREAMCATCHER_DYE_SERIALIZER = entry("dreamcatcher_dye", { SimpleCraftingRecipeSerializer(::DreamCatcherDyeRecipe) }) {}
        val LIQUID_ESTROGEN_CAULDRON_SERIALIZER = codecSerializer("liquid_estrogen_cauldron", {LiquidEstrogenCauldronRecipe.CODEC}, {LiquidEstrogenCauldronRecipe.NET_CODEC}) {}
    }
}