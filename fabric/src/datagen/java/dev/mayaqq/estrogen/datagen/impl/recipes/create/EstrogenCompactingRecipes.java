package dev.mayaqq.estrogen.datagen.impl.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.FabricRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.ForgeRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenCompactingRecipes extends BaseRecipeProvider {

    public EstrogenCompactingRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        create(id("slime_ball"), recipeBuilder -> recipeBuilder
                .require(EstrogenFluids.MOLTEN_SLIME.get(), getAmount(54000))
                .output(Items.SLIME_BALL, 1)
        );
    }

    public static EstrogenCompactingRecipes buildFabric(FabricDataOutput output) {
        return new EstrogenCompactingRecipes(output, new FabricRecipeHelper());
    }

    public static EstrogenCompactingRecipes buildForge(FabricDataOutput output) {
        return new EstrogenCompactingRecipes(output, new ForgeRecipeHelper());
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.COMPACTING;
    }
}
