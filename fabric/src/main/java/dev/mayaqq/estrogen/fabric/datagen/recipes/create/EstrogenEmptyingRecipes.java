package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeFabricImpl;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeForgeImpl;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeInterface;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenEmptyingRecipes<T extends EstrogenRecipeInterface> extends ProcessingRecipeGen {

    private T t;
    GeneratedRecipe HORSE_URINE = create(id("horse_urine"), recipeBuilder -> recipeBuilder
            .require(EstrogenItems.HORSE_URINE_BOTTLE.get())
            .output(EstrogenFluids.HORSE_URINE.get(), t.getAmount(27000))
            .output(Items.GLASS_BOTTLE));

    public EstrogenEmptyingRecipes(FabricDataOutput output, T t) {
        super(output);
        this.t = t;
    }

    public static EstrogenEmptyingRecipes<?> buildFabric(FabricDataOutput output) {
        return new EstrogenEmptyingRecipes<>(output, new EstrogenRecipeFabricImpl());
    }

    public static EstrogenEmptyingRecipes<?> buildForge(FabricDataOutput output) {
        return new EstrogenEmptyingRecipes<>(output, new EstrogenRecipeForgeImpl());
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.EMPTYING;
    }

    @Override
    public String getName() {
        return t.getName(super.getName());
    }
}
