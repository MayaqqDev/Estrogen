package dev.mayaqq.estrogen.datagen.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.item.Items;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenEmptyingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe HORSE_URINE = create(id("horse_urine"), recipeBuilder -> recipeBuilder
            .require(EstrogenItems.HORSE_URINE_BOTTLE)
            .output(EstrogenFluids.HORSE_URINE.still(), 27000)
            .output(Items.GLASS_BOTTLE));

    public EstrogenEmptyingRecipes(FabricDataGenerator fdg) {
        super(fdg);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.EMPTYING;
    }
}
