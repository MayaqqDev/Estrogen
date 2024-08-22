package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.fabric.datagen.custom.EstrogenProcessingRecipeGen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class EstrogenSplashingRecipes extends EstrogenProcessingRecipeGen {

    private final GeneratedRecipe THIGH_HIGH_WASHING =
        create(Estrogen.id("thigh_high_washing"), builder -> builder
                .require(EstrogenItems.THIGH_HIGHS.get())
                .duration(800)
                .output(EstrogenItems.THIGH_HIGHS.get())
            );


    public EstrogenSplashingRecipes(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.SPLASHING;
    }
}
