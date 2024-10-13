package dev.mayaqq.estrogen.datagen.base.recipes;

import com.google.gson.JsonObject;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class CustomFinishedRecipe<S extends ProcessingRecipe<?>> implements FinishedRecipe {

    private final Supplier<PlatformRecipeHelper.EstrogenLoadCondition> condition;
    private final ProcessingRecipeSerializer<S> serializer;
    private final ResourceLocation id;
    private final S recipe;

    public CustomFinishedRecipe(S recipe, @Nullable Supplier<PlatformRecipeHelper.EstrogenLoadCondition> conditions) {
        this.recipe = recipe;
        this.condition = conditions;
        IRecipeTypeInfo recipeType = this.recipe.getTypeInfo();
        ResourceLocation typeId = recipeType.id();

        if (!(recipeType.getSerializer() instanceof ProcessingRecipeSerializer))
            throw new IllegalStateException("Cannot datagen ProcessingRecipe of type: " + typeId);

        this.id = new ResourceLocation(recipe.getId().getNamespace(), typeId.getPath() + "/" + recipe.getId().getPath());
        this.serializer = (ProcessingRecipeSerializer<S>) recipe.getSerializer();
    }

    @Override
    public void serializeRecipeData(@NotNull JsonObject json) {
        this.serializer.write(json, recipe);
        if (condition == null) return;
        PlatformRecipeHelper.EstrogenLoadCondition condition = this.condition.get();
        json.add(condition.name(), condition.condition());
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getType() {
        return serializer;
    }

    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }

}
