package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenCompactingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe SLIME_BALL = create(id("slime_ball"), recipeBuilder -> recipeBuilder
            .require(EstrogenFluids.MOLTEN_SLIME.get(), 54000)
            .output(Items.SLIME_BALL, 1));

    public EstrogenCompactingRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.COMPACTING;
    }
}
