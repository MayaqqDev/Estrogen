package dev.mayaqq.estrogen.datagen.impl.recipes.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.FabricRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.ForgeRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenMixingRecipes extends BaseRecipeProvider {

    public EstrogenMixingRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        create(id("filtrated_horse_urine"), recipeBuilder -> recipeBuilder
                .require(EstrogenFluids.HORSE_URINE.get(), getAmount(27000))
                .require(AllItems.FILTER.get())
                .output(EstrogenFluids.FILTRATED_HORSE_URINE.get(), getAmount(27000))
                .output(EstrogenItems.USED_FILTER.get())
        );

        create(id("molten_amethyst"), recipeBuilder -> recipeBuilder
                .require(Items.AMETHYST_SHARD)
                .output(EstrogenFluids.MOLTEN_AMETHYST.get(), getAmount(27000))
                .requiresHeat(HeatCondition.HEATED)
        );

        create(id("balls"), recipeBuilder -> recipeBuilder
                .require(Items.SLIME_BALL)
                .output(EstrogenItems.BALLS.get())
                .output(EstrogenFluids.MOLTEN_SLIME.get(), getAmount(27000))
                .requiresHeat(HeatCondition.HEATED)
        );

        create(id("testosterone_mixture"), recipeBuilder -> recipeBuilder
                .require(EstrogenItems.TESTOSTERONE_POWDER.get())
                .require(Items.COAL)
                .output(EstrogenFluids.TESTOSTERONE_MIXTURE.get(), getAmount(54000))
                .requiresHeat(HeatCondition.HEATED)
        );

        create(id("tinted_glass"), recipeBuilder -> recipeBuilder
                .require(Items.GLASS)
                .require(EstrogenFluids.MOLTEN_AMETHYST.get(), getAmount(27000))
                .output(Items.TINTED_GLASS)
        );
    }

    public static EstrogenMixingRecipes buildFabric(FabricDataOutput output) {
        return new EstrogenMixingRecipes(output, new FabricRecipeHelper());
    }

    public static EstrogenMixingRecipes buildForge(FabricDataOutput output) {
        return new EstrogenMixingRecipes(output, new ForgeRecipeHelper());
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MIXING;
    }
}
