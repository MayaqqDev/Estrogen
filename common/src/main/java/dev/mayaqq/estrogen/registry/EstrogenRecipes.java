package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import dev.mayaqq.estrogen.registry.recipes.ThighHighDyeRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import uwu.serenity.critter.api.entry.RegistryEntry;
import uwu.serenity.critter.api.generic.Registrar;

public class EstrogenRecipes {
    public static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = Estrogen.REGISTRIES.getRegistrar(Registries.RECIPE_SERIALIZER);
    public static final Registrar<RecipeType<?>> RECIPE_TYPES = Estrogen.REGISTRIES.getRegistrar(Registries.RECIPE_TYPE);

    public static final RegistryEntry<RecipeType<CentrifugingRecipe>> CENTRIFUGING = register("centrifuging");
    public static final RegistryEntry<ProcessingRecipeSerializer<CentrifugingRecipe>> CENTRIFUGING_SERIALIZER =
            RECIPE_SERIALIZERS.entry("centrifuging", () -> new ProcessingRecipeSerializer<>(CentrifugingRecipe::new)).register();
    public static final RegistryEntry<RecipeType<EntityInteractionRecipe>> ENTITY_INTERACTION = register("entity_interaction");
    public static final RegistryEntry<CodecRecipeSerializer<EntityInteractionRecipe>> ENTITY_INTERACTION_SERIALIZER =
            RECIPE_SERIALIZERS.entry("entity_interaction", () -> new CodecRecipeSerializer<>(ENTITY_INTERACTION.get(), EntityInteractionRecipe::codec, EntityInteractionRecipe::netCodec)).register();
    public static final RegistryEntry<SimpleCraftingRecipeSerializer<ThighHighDyeRecipe>> THIGH_HIGH_DYE_SERIALIZER =
            RECIPE_SERIALIZERS.entry("thigh_high_dye", () -> new SimpleCraftingRecipeSerializer<>(ThighHighDyeRecipe::new)).register();

    private static <T extends Recipe<?>> RegistryEntry<RecipeType<T>> register(String id) {
        return RECIPE_TYPES.<RecipeType<T>>entry(id, () -> new RecipeType<>() {
            public String toString() {
                return id;
            }
        }).register();
    }
}
