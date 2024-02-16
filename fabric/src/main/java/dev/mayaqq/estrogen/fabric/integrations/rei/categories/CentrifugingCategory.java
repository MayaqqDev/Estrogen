package dev.mayaqq.estrogen.fabric.integrations.rei.categories;

import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import com.simibubi.create.compat.rei.category.WidgetUtil;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.mayaqq.estrogen.fabric.integrations.rei.animated.AnimatedCentrifuge;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.ArrayList;
import java.util.List;

public class CentrifugingCategory extends CreateRecipeCategory<CentrifugingRecipe> {

    public CentrifugingCategory(Info<CentrifugingRecipe> info) {
        super(info);
    }

    @Override
    public List<Widget> setupDisplay(CreateDisplay<CentrifugingRecipe> display, Rectangle bounds) {

        Point origin = new Point(bounds.getX(), bounds.getY() + 4);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        FluidStack inputStack = display.getRecipe().getFluidIngredients().get(0).getMatchingFluidStacks().get(0);
        FluidStack outputStack = display.getRecipe().getFluidResults().get(0);

        widgets.add(Widgets.createRecipeBase(bounds));

        int xInput = 34;
        int xOutput = 107;
        int y = 47;

        widgets.add(WidgetUtil.textured(AllGuiTextures.JEI_SLOT, origin.getX() + (xInput - 1), origin.getY() + (y - 1)));
        widgets.add(WidgetUtil.textured(AllGuiTextures.JEI_SLOT, origin.getX() + (xOutput - 1), origin.getY() + (y - 1)));
        Slot fluidInputSlot = basicSlot(xInput, y, origin).markInput().entries(EntryIngredients.of(convertToREIFluid(inputStack)));
        widgets.add(fluidInputSlot);

        Slot fluidOutputSlot = basicSlot(xOutput, y, origin).markInput().entries(EntryIngredients.of(convertToREIFluid(outputStack)));
        widgets.add(fluidOutputSlot);

        widgets.add(WidgetUtil.textured(AllGuiTextures.JEI_ARROW, origin.getX() + 60, origin.getY() + (y - 1)));

        AnimatedCentrifuge centrifuge = new AnimatedCentrifuge();
        centrifuge.setPos(new Point(origin.getX() + 70, origin.getY() + 20));
        widgets.add(centrifuge);
        return widgets;
    }
}
