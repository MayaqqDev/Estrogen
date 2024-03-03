package dev.mayaqq.estrogen.registry.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record EntityInteractionRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, String entity) implements CodecRecipe<Container> {

    /* TODO: Recipe
    SO, I need to make a custom Entity codec, so please do that in the future, another thing is use ad astra as reference, it should basically work.
    https://github.com/terrarium-earth/Ad-Astra/blob/1.20.1/common/src/main/java/earth/terrarium/adastra/common/recipes/machines/CompressingRecipe.java

    Do not forget we are not using a container.
     */

    @Override
    public @NotNull ResourceLocation id() {
        return null;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }
}
