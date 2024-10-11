package dev.mayaqq.estrogen.datagen.base.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class BaseRecipeProvider extends ProcessingRecipeGen {

    private final PlatformRecipeHelper helper;

    public BaseRecipeProvider(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output);
        this.helper = helper;

        init();
    }

    protected abstract void init();

    protected GeneratedRecipe add(GeneratedRecipe recipe) {
        all.add(recipe);
        return recipe;
    }

    protected GeneratedRecipe add(RecipeBuilder builder) {
        return add(builder::save);
    }

    protected <T extends ProcessingRecipe<?>> GeneratedRecipe createMulti(ResourceLocation name, UnaryOperator<ProcessingRecipeBuilder<T>> transform, Supplier<PlatformRecipeHelper.EstrogenLoadCondition> condition) {
        ProcessingRecipeSerializer<T> serializer = getSerializer();
        GeneratedRecipe generatedRecipe = c -> buildCustom(transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), name)), c, condition);
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    protected <T extends ProcessingRecipe<?>> void buildCustom(ProcessingRecipeBuilder<T> transform, Consumer<FinishedRecipe> consumer, Supplier<PlatformRecipeHelper.EstrogenLoadCondition> condition) {
        consumer.accept(new CustomFinishedRecipe<>(transform.build(), condition));
    }

    public long getAmount(long amount) {
        return helper.getFluidAmount(amount);
    }

    public String getRecipeName() {
        return getRecipeType().id().getPath();
    }

    @Override
    public String getName() {
        return helper.getName(
            "Estrogen's Processing Recipes: " + getRecipeName()
        );
    }

    public PlatformRecipeHelper.EstrogenLoadCondition isModLoaded(String modid) {
        return helper.isModLoaded(modid);
    }

    public TagKey<Item> getCommonTag(String name) {
        return helper.getCommonTag(name);
    }
}
