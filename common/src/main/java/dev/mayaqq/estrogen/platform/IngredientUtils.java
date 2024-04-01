package dev.mayaqq.estrogen.platform;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import dev.architectury.injectables.annotations.ExpectPlatform;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;

import java.util.List;

public class IngredientUtils {
    @ExpectPlatform
    public static List<FluidHolder> getFluidIngredients(ProcessingRecipe<?> recipe) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static List<FluidHolder> getFluidResults(ProcessingRecipe<?> recipe) {
        throw new AssertionError();
    }
}
