package dev.mayaqq.estrogen.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;

public class JeiPerPlatform {
    @ExpectPlatform
    public static void setRecipe(IRecipeLayoutBuilder builder, CentrifugingRecipe recipe, IFocusGroup focuses) {}
}
