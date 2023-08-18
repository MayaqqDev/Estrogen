package dev.mayaqq.estrogen.datagen.recipes.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.item.Items;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenMixingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe
        FILATRATED_HORSE_URINE = create(id("filtrated_horse_urine"), recipeBuilder -> recipeBuilder
            .require(EstrogenFluids.HORSE_URINE.still(), 27000)
            .require(AllItems.FILTER.get())
            .output(EstrogenFluids.FILTRATED_HORSE_URINE.still(), 27000)
            .output(EstrogenItems.USED_FILTER)),
        MOLTEN_AMETHYST = create(id("molten_amethyst"), recipeBuilder -> recipeBuilder
                .require(Items.AMETHYST_SHARD)
                .output(EstrogenFluids.MOLTEN_AMETHYST.still(), 27000)
                .requiresHeat(HeatCondition.HEATED)),
        BALLS = create(id("balls"), recipeBuilder -> recipeBuilder
                .require(Items.SLIME_BALL)
                .output(EstrogenItems.BALLS)
                .output(EstrogenFluids.MOLTEN_SLIME.still(), 27000)
                .requiresHeat(HeatCondition.HEATED)),
        TESTOSTERONE_MIXTURE = create(id("testosterone_mixture"), recipeBuilder -> recipeBuilder
                .require(EstrogenItems.TESTOSTERONE_POWDER)
                .require(Items.COAL)
                .output(EstrogenFluids.TESTOSTERONE_MIXTURE.still(), 54000)
                .requiresHeat(HeatCondition.HEATED));

    public EstrogenMixingRecipes(FabricDataGenerator fdg) {
        super(fdg);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MIXING;
    }
}
