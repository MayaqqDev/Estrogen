package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import dev.mayaqq.estrogen.registry.recipes.ThighHighDyeRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class EstrogenRecipes {

    public static final EstrogenRecipes RECIPES = new EstrogenRecipes();

    public static final RegistryEntry<RecipeType<EntityInteractionRecipe>> ENTITY_INTERACTION = register("entity_interaction");
    public static final RegistryEntry<CodecRecipeSerializer<EntityInteractionRecipe>> ENTITY_INTERACTION_SERIALIZER =
            EstrogenRecipeRegistries.RECIPE_SERIALIZERS.register("entity_interaction", () -> new CodecRecipeSerializer<>(ENTITY_INTERACTION.get(), EntityInteractionRecipe::codec, EntityInteractionRecipe::netCodec));
    public static final RegistryEntry<SimpleCraftingRecipeSerializer<ThighHighDyeRecipe>> THIGH_HIGH_DYE_SERIALIZER =
            EstrogenRecipeRegistries.RECIPE_SERIALIZERS.register("thigh_high_dye", () -> new SimpleCraftingRecipeSerializer<>(ThighHighDyeRecipe::new));

    private static <T extends Recipe<?>> RegistryEntry<RecipeType<T>> register(String id) {
        return EstrogenRecipeRegistries.RECIPE_TYPES.register(id, () -> new RecipeType<>() {
            public String toString() {
                return id;
            }
        });
    }

    public void init() {}
}
