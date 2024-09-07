package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import dev.mayaqq.estrogen.registry.recipes.ThighHighDyeRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import uwu.serenity.critter.api.entry.RegistryEntry;

public class EstrogenRecipes {

    public static final EstrogenRecipes RECIPES = new EstrogenRecipes();

    public static final RegistryEntry<RecipeType<EntityInteractionRecipe>> ENTITY_INTERACTION = register("entity_interaction");
    public static final RegistryEntry<CodecRecipeSerializer<EntityInteractionRecipe>> ENTITY_INTERACTION_SERIALIZER =
            EstrogenRecipeRegistries.RECIPE_SERIALIZERS.entry("entity_interaction", () -> new CodecRecipeSerializer<>(ENTITY_INTERACTION.get(), EntityInteractionRecipe::codec, EntityInteractionRecipe::netCodec)).register();
    public static final RegistryEntry<SimpleCraftingRecipeSerializer<ThighHighDyeRecipe>> THIGH_HIGH_DYE_SERIALIZER =
            EstrogenRecipeRegistries.RECIPE_SERIALIZERS.entry("thigh_high_dye", () -> new SimpleCraftingRecipeSerializer<>(ThighHighDyeRecipe::new)).register();

    private static <T extends Recipe<?>> RegistryEntry<RecipeType<T>> register(String id) {
        return EstrogenRecipeRegistries.RECIPE_TYPES.<RecipeType<T>>entry(id, () -> new RecipeType<>() {
            public String toString() {
                return id;
            }
        }).register();
    }

    public void init() {}
}
