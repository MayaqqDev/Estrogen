package dev.mayaqq.estrogen.datagen.impl.recipes.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.FabricRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.ForgeRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.registry.EstrogenCreativeTab;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenPotions;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenFillingRecipes extends BaseRecipeProvider {

    public EstrogenFillingRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        create(id("crystal_estrogen_pill"), recipeBuilder -> recipeBuilder
                .require(EstrogenItems.ESTROGEN_PILL.get())
                .require(EstrogenFluids.MOLTEN_AMETHYST.get(), getAmount(27000))
                .output(EstrogenItems.CRYSTAL_ESTROGEN_PILL.get(), 1)
        );

        create(id("estrogen_pill"), recipeBuilder -> recipeBuilder
                .require(Items.COOKIE)
                .require(EstrogenFluids.LIQUID_ESTROGEN.get(), getAmount(27000))
                .output(EstrogenItems.ESTROGEN_PILL.get(), 1)
        );

        create(id("filter"), recipeBuilder -> recipeBuilder
                .require(EstrogenItems.USED_FILTER.get())
                .require(Fluids.WATER, getAmount(27000))
                .output(AllItems.FILTER.get(), 1)
        );

        create(id("estrogen_tipped_arrow"), recipeBuilder -> recipeBuilder
                .require(Items.ARROW)
                .require(EstrogenFluids.LIQUID_ESTROGEN.get(), getAmount(27000))
                .output(EstrogenCreativeTab.tippedArrow(EstrogenPotions.ESTROGEN_POTION.get()))
        );

        createMulti(id("blahaj"), recipeBuilder -> recipeBuilder
                        .require(Items.LIGHT_BLUE_WOOL)
                        .require(EstrogenFluids.LIQUID_ESTROGEN.get(), getAmount(81))
                        .output(100, new ResourceLocation("blahaj", "blue_shark"), 1),
                () -> isModLoaded("blahaj")
        );
    }

    public static EstrogenFillingRecipes buildFabric(FabricDataOutput output) {
        return new EstrogenFillingRecipes(output, new FabricRecipeHelper());
    }

    public static EstrogenFillingRecipes buildForge(FabricDataOutput output) {
        return new EstrogenFillingRecipes(output, new ForgeRecipeHelper());
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.FILLING;
    }
}
