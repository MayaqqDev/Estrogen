package dev.mayaqq.estrogen.utils.recipe;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public record RecipeTypeInfo(ResourceLocation id, RecipeType<?> type, RecipeSerializer<?> serializer) implements IRecipeTypeInfo {
        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return this.serializer;
        }

        @Override
        public RecipeType<?> getType() {
            return this.type;
        }
    }