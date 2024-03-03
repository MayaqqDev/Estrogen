package dev.mayaqq.estrogen.integrations.jei.categories;


import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.mayaqq.estrogen.integrations.jei.categories.animations.AnimatedCentrifuge;
import dev.mayaqq.estrogen.platform.JeiPerPlatform;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CentrifugingCategory extends CreateRecipeCategory<CentrifugingRecipe> {

    private final AnimatedCentrifuge centrifuge = new AnimatedCentrifuge();

    public CentrifugingCategory(Info<CentrifugingRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CentrifugingRecipe recipe, IFocusGroup focuses) {
        JeiPerPlatform.setRecipe(builder, recipe, focuses);
    }

    @Override
    public void draw(CentrifugingRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 126, 29);
        centrifuge.draw(graphics, getBackground().getWidth() / 2 - 13, 22);
    }

}