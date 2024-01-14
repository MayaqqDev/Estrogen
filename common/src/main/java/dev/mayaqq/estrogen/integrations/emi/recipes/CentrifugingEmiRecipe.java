package dev.mayaqq.estrogen.integrations.emi.recipes;

import com.simibubi.create.compat.emi.recipes.CreateEmiRecipe;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.mayaqq.estrogen.integrations.emi.EmiCompat;
import dev.mayaqq.estrogen.integrations.emi.EstrogenEmiAnimations;
import dev.mayaqq.estrogen.registry.common.recipes.CentrifugingRecipe;

import java.util.List;

public class CentrifugingEmiRecipe extends CreateEmiRecipe<CentrifugingRecipe> {

    public CentrifugingEmiRecipe(CentrifugingRecipe recipe) {
        super(EmiCompat.CENTRIFUGING, recipe, 134, 80, (r) -> {});
        this.input = List.of(firstFluidOrEmpty(recipe.getFluidIngredients().get(0).getMatchingFluidStacks()));
        this.output = List.of(firstFluidOrEmpty(recipe.getFluidResults()));
    }


        @Override
    public void addWidgets(WidgetHolder widgets) {
            addTexture(widgets, AllGuiTextures.JEI_LONG_ARROW, 31, 51);
            addTexture(widgets, AllGuiTextures.JEI_SHADOW, 40, 36);

            addSlot(widgets, input.get(0), 5, 51);

            addSlot(widgets, output.get(0), 110, 51).recipeContext(this);

            EstrogenEmiAnimations.addCentrifuge(widgets, 55, 40);
    }
}
