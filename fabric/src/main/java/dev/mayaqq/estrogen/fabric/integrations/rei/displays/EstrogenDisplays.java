package dev.mayaqq.estrogen.fabric.integrations.rei.displays;

import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.Arrays;
import java.util.List;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenDisplays {
    public static CreateDisplay<CentrifugingRecipe> centrifuging(CentrifugingRecipe recipe) {
        return new CreateDisplay<>(recipe, CategoryIdentifier.of(id("centrifuging")), List.of(EntryIngredients.of(CreateRecipeCategory.convertToREIFluid(recipe.getFluidIngredients().get(0).getMatchingFluidStacks().get(0)))), List.of(EntryIngredients.of(CreateRecipeCategory.convertToREIFluid(recipe.getFluidResults().get(0)))));
    }

    public static CreateDisplay<EntityInteractionRecipe> entityInteraction(EntityInteractionRecipe recipe) {
        return new CreateDisplay<>(recipe, CategoryIdentifier.of(id("entity_interaction")), List.of(EntryIngredients.ofIngredient(recipe.ingredient())), List.of(EntryIngredients.of(recipe.result()), EntryIngredients.ofItemStacks(Arrays.asList(recipe.entity().spawnEggs()))));
    }
}