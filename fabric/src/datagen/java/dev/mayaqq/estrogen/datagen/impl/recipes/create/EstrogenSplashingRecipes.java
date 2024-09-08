package dev.mayaqq.estrogen.datagen.impl.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class EstrogenSplashingRecipes extends BaseRecipeProvider {

    public EstrogenSplashingRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        create(Estrogen.id("thigh_high_washing"), builder -> builder
                .require(EstrogenItems.THIGH_HIGHS.get())
                .duration(800)
                .output(EstrogenItems.THIGH_HIGHS.get())
        );
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.SPLASHING;
    }
}
