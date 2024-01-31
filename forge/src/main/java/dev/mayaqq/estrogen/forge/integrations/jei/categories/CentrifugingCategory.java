package dev.mayaqq.estrogen.forge.integrations.jei.categories;


import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.mayaqq.estrogen.forge.integrations.jei.categories.animations.AnimatedCentrifuge;
import dev.mayaqq.estrogen.registry.common.recipes.CentrifugingRecipe;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CentrifugingCategory extends CreateRecipeCategory<CentrifugingRecipe> {

	private final AnimatedCentrifuge centrifuge = new AnimatedCentrifuge();

	public CentrifugingCategory(Info<CentrifugingRecipe> info) {
		super(info);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, CentrifugingRecipe recipe, IFocusGroup focuses) {
		builder
				.addSlot(RecipeIngredientRole.INPUT, 27, 32)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(ForgeTypes.FLUID_STACK, withImprovedVisibility(recipe.getFluidIngredients().get(0).getMatchingFluidStacks()))
				.addTooltipCallback(addFluidTooltip(recipe.getFluidIngredients().get(0).getRequiredAmount()));
		builder
				.addSlot(RecipeIngredientRole.OUTPUT, 132, 51)
				.setBackground(getRenderedSlot(), -1, -1)
				.addFluidStack(recipe.getFluidResults().get(0).getFluid(), recipe.getFluidResults().get(0).getAmount());
	}

	@Override
	public void draw(CentrifugingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, PoseStack graphics, double mouseX, double mouseY) {
		AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 126, 29);
		centrifuge.draw(graphics, getBackground().getWidth() / 2 - 13, 22);
	}

}