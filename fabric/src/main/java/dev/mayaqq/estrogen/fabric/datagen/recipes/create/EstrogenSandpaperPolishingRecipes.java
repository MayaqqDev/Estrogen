package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenSandpaperPolishingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe
            TESTOSTERONE_CHUNK = create(id("testosterone_chunk"), recipeBuilder -> recipeBuilder
            .require(EstrogenItems.BALLS.get())
            .output(EstrogenItems.TESTOSTERONE_CHUNK.get(), 1));

    public EstrogenSandpaperPolishingRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.SANDPAPER_POLISHING;
    }
}
