package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeFabricImpl;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeForgeImpl;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeInterface;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenFillingRecipes<T extends EstrogenRecipeInterface> extends ProcessingRecipeGen {

    private T t;
    GeneratedRecipe
        CRYSTAL_ESTROGEN_PILL = create(id("crystal_estrogen_pill"), recipeBuilder -> recipeBuilder
                .require(EstrogenItems.ESTROGEN_PILL)
                .require(EstrogenFluids.MOLTEN_AMETHYST.get(), t.getAmount(27000))
                .output(EstrogenItems.CRYSTAL_ESTROGEN_PILL, 1)),
        ESTROGEN_PILL = create(id("estrogen_pill"), recipeBuilder -> recipeBuilder
                .require(Items.COOKIE)
                .require(EstrogenFluids.LIQUID_ESTROGEN.get(), t.getAmount(27000))
                .output(EstrogenItems.ESTROGEN_PILL, 1)),
        FILTER = create(id("filter"), recipeBuilder -> recipeBuilder
                .require(EstrogenItems.USED_FILTER)
                .require(Fluids.WATER, t.getAmount(27000))
                .output(AllItems.FILTER.get(), 1));


    public EstrogenFillingRecipes(FabricDataOutput output, T t) {
        super(output);
        this.t = t;
    }

    public static EstrogenFillingRecipes buildFabric(FabricDataOutput output) {
        return new EstrogenFillingRecipes<>(output, new EstrogenRecipeFabricImpl());
    }

    public static EstrogenFillingRecipes buildForge(FabricDataOutput output) {
        return new EstrogenFillingRecipes<>(output, new EstrogenRecipeForgeImpl());
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.FILLING;
    }

    @Override
    protected ResourceLocation getRecipeIdentifier(ResourceLocation identifier) {
        return t.getRecipeIdentifier(identifier);
    }

    @Override
    public String getName() {
        return t.getName(super.getName());
    }
}
