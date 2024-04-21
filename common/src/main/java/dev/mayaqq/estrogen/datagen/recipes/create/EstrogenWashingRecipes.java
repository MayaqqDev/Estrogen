package dev.mayaqq.estrogen.datagen.recipes.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenWashingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe
            FILTER = create(id("filter"), recipeBuilder -> recipeBuilder
                    .require(EstrogenItems.USED_FILTER.get())
                    .output(AllItems.FILTER.get(), 1));

    public EstrogenWashingRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.SPLASHING;
    }
}
