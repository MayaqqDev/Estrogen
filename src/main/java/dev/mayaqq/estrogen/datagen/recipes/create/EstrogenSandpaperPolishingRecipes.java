package dev.mayaqq.estrogen.datagen.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenSandpaperPolishingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe
        TESTOSTERONE_CHUNK = create(id("testosterone_chunk"), recipeBuilder -> recipeBuilder
            .require(EstrogenItems.BALLS)
            .output(EstrogenItems.TESTOSTERONE_CHUNK, 1));

    public EstrogenSandpaperPolishingRecipes(FabricDataGenerator fdg) {
        super(fdg);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.SANDPAPER_POLISHING;
    }
}
