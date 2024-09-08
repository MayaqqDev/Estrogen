package dev.mayaqq.estrogen.datagen.impl.recipes.estrogen;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.FabricRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.ForgeRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenProcessingRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import static dev.mayaqq.estrogen.Estrogen.id;


public class EstrogenCentrifugingRecipes extends BaseRecipeProvider {

    public EstrogenCentrifugingRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        create(id("liquid_estrogen"), recipeBuilder -> recipeBuilder
                .require(EstrogenFluids.FILTRATED_HORSE_URINE.get(), getAmount(81))
                .output(EstrogenFluids.LIQUID_ESTROGEN.get(), getAmount(81))
        );
    }

    public static EstrogenCentrifugingRecipes buildFabric(FabricDataOutput output) {
        return new EstrogenCentrifugingRecipes(output, new FabricRecipeHelper());
    }

    public static EstrogenCentrifugingRecipes buildForge(FabricDataOutput output) {
        return new EstrogenCentrifugingRecipes(output, new ForgeRecipeHelper());
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return EstrogenProcessingRecipes.CENTRIFUGING;
    }
}
