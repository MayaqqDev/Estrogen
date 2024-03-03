package dev.mayaqq.estrogen.platform.forge;

import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

import static com.simibubi.create.compat.jei.category.CreateRecipeCategory.*;

public class JeiPerPlatformImpl {

    public static void setRecipe(IRecipeLayoutBuilder builder, CentrifugingRecipe recipe, IFocusGroup focuses) {
        IRecipeSlotBuilder slotBuilder1 = builder.addSlot(RecipeIngredientRole.INPUT, 27, 32);
        slotBuilder1.setBackground(getRenderedSlot(), -1, -1);
        slotBuilder1.addIngredients(ForgeTypes.FLUID_STACK, withImprovedVisibility(recipe.getFluidIngredients().get(0).getMatchingFluidStacks()));
        slotBuilder1.addTooltipCallback(addFluidTooltip(recipe.getFluidIngredients().get(0).getRequiredAmount()));

        IRecipeSlotBuilder slotBuilder2 = builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 51);
        slotBuilder2.setBackground(getRenderedSlot(), -1, -1);
        slotBuilder2.addFluidStack(recipe.getFluidResults().get(0).getFluid(), recipe.getFluidResults().get(0).getAmount());
        slotBuilder2.addTooltipCallback(addFluidTooltip(recipe.getFluidIngredients().get(0).getRequiredAmount()));
    }
}
