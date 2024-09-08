package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import uwu.serenity.critter.api.generic.Registrar;

public class EstrogenRecipeRegistries {
    public static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = Estrogen.REGISTRIES.getRegistrar(Registries.RECIPE_SERIALIZER);
    public static final Registrar<RecipeType<?>> RECIPE_TYPES = Estrogen.REGISTRIES.getRegistrar(Registries.RECIPE_TYPE);
}
