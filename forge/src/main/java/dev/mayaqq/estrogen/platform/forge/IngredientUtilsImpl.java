package dev.mayaqq.estrogen.platform.forge;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientUtilsImpl {

    public static List<FluidHolder> getFluidIngredients(ProcessingRecipe<?> recipe) {
        return recipe.getFluidIngredients().stream()
                .map(ingredient -> FluidHolder.of(ingredient.getMatchingFluidStacks().get(0).getFluid(), ingredient.getRequiredAmount()))
                .collect(Collectors.toList());
    }

    public static List<FluidHolder> getFluidResults(ProcessingRecipe<?> recipe) {
        List<FluidHolder> results = new ArrayList<>();
        recipe.getFluidResults().forEach(fluidStack -> {
            results.add(FluidHolder.of(fluidStack.getFluid(), fluidStack.getAmount()));
        });
        return results;
    }
}