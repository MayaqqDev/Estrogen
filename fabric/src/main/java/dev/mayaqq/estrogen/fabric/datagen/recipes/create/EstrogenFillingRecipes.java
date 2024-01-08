package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenFillingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe
        CRYSTAL_ESTROGEN_PILL = create(id("crystal_estrogen_pill"), recipeBuilder -> recipeBuilder
                .require(EstrogenItems.ESTROGEN_PILL)
                .require(EstrogenFluids.MOLTEN_AMETHYST.get(), 27000)
                .output(EstrogenItems.CRYSTAL_ESTROGEN_PILL, 1)),
        ESTROGEN_PILL = create(id("estrogen_pill"), recipeBuilder -> recipeBuilder
                .require(Items.COOKIE)
                .require(EstrogenFluids.LIQUID_ESTROGEN.get(), 27000)
                .output(EstrogenItems.ESTROGEN_PILL, 1)),
        FILTER = create(id("filter"), recipeBuilder -> recipeBuilder
                .require(EstrogenItems.USED_FILTER)
                .require(Fluids.WATER, 27000)
                .output(AllItems.FILTER.get(), 1));


    public EstrogenFillingRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.FILLING;
    }
}
