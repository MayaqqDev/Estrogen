package dev.mayaqq.estrogen.datagen.impl.recipes.estrogen;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import dev.mayaqq.estrogen.registry.recipes.CentrifugingRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import static dev.mayaqq.estrogen.Estrogen.id;


public class EstrogenCentrifugingRecipes extends BaseRecipeProvider {

    public EstrogenCentrifugingRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        create(id("liquid_estrogen"), builder -> builder
                .require(EstrogenFluids.FILTRATED_HORSE_URINE.get(), getAmount(81))
                .output(EstrogenFluids.LIQUID_ESTROGEN.get(), getAmount(81))
        );
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return CentrifugingRecipe.getRecipeTypeInfo();
    }
}
