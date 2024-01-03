package dev.mayaqq.estrogen.fabric.datagen.recipes.estrogen;

import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import static dev.mayaqq.estrogen.Estrogen.id;


public class EstrogenCentrifugingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe	LIQUID_ESTROGEN = create(id("liquid_estrogen"), recipeBuilder -> recipeBuilder
            .require(EstrogenFluids.FILTRATED_HORSE_URINE.still().get(), 10)
            .output(EstrogenFluids.LIQUID_ESTROGEN.still().get(), 10));

    public EstrogenCentrifugingRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return EstrogenRecipes.CENTRIFUGING;
    }
}
