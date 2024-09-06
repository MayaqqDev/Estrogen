package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import uwu.serenity.critter.stdlib.Registrar;

public class EstrogenRecipeRegistries {
    public static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = Registrar.create(Estrogen.MOD_ID, Registries.RECIPE_SERIALIZER);
    public static final Registrar<RecipeType<?>> RECIPE_TYPES = Registrar.create(Estrogen.MOD_ID, Registries.RECIPE_TYPE);
}
