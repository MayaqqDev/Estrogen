package dev.mayaqq.estrogen.fabric.datagen.custom;

import com.google.gson.JsonObject;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeInterface;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class EstrogenProcessingRecipeGen extends ProcessingRecipeGen {
    public EstrogenProcessingRecipeGen(FabricDataOutput generator) {
        super(generator);
    }

    protected <T extends ProcessingRecipe<?>> CreateRecipeProvider.GeneratedRecipe createMulti(ResourceLocation name, UnaryOperator<ProcessingRecipeBuilder<T>> transform, Supplier<EstrogenRecipeInterface.EstrogenLoadCondition> condition) {
        ProcessingRecipeSerializer<T> serializer = getSerializer();
        CreateRecipeProvider.GeneratedRecipe generatedRecipe = c -> buildCustom(transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), name)), c, condition);
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    protected <T extends ProcessingRecipe<?>> void buildCustom(ProcessingRecipeBuilder<T> transform, Consumer<FinishedRecipe> consumer, Supplier<EstrogenRecipeInterface.EstrogenLoadCondition> condition) {
        consumer.accept(new DataGenResult<>(transform.build(), condition));
    }

    public static class DataGenResult<S extends ProcessingRecipe<?>> implements FinishedRecipe {

        private Supplier<EstrogenRecipeInterface.EstrogenLoadCondition> condition;
        private ProcessingRecipeSerializer<S> serializer;
        private ResourceLocation id;
        private S recipe;

        @SuppressWarnings("unchecked")
        public DataGenResult(S recipe, @Nullable Supplier<EstrogenRecipeInterface.EstrogenLoadCondition> conditions) {
            this.recipe = recipe;
            this.condition = conditions;
            IRecipeTypeInfo recipeType = this.recipe.getTypeInfo();
            ResourceLocation typeId = recipeType.getId();

            if (!(recipeType.getSerializer() instanceof ProcessingRecipeSerializer))
                throw new IllegalStateException("Cannot datagen ProcessingRecipe of type: " + typeId);

            this.id = new ResourceLocation(recipe.getId().getNamespace(),
                    typeId.getPath() + "/" + recipe.getId().getPath());
            this.serializer = (ProcessingRecipeSerializer<S>) recipe.getSerializer();
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            serializer.write(json, recipe);
            if (condition == null) return;
            EstrogenRecipeInterface.EstrogenLoadCondition condition = this.condition.get();
            json.add(condition.name(), condition.condition());
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
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
}
