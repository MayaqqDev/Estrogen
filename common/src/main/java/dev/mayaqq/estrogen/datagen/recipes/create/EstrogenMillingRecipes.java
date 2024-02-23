package dev.mayaqq.estrogen.datagen.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.EstrogenCreateItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenMillingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe
            TESTOSTERONE_POWDER = create(id("testosterone_powder"), recipeBuilder -> recipeBuilder
            .require(EstrogenCreateItems.TESTOSTERONE_CHUNK)
            .output(EstrogenCreateItems.TESTOSTERONE_POWDER, 3));


    public EstrogenMillingRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MILLING;
    }
}
