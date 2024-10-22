package dev.mayaqq.estrogen.datagen.impl.recipes.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
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
        create(id("filtrated_horse_urine"), builder -> builder
                .require(EstrogenFluids.HORSE_URINE.get(), getAmount(27000))
                .require(AllItems.FILTER)
                .output(EstrogenFluids.FILTRATED_HORSE_URINE.get(), getAmount(27000))
                .output(EstrogenItems.USED_FILTER)
        );

        create(id("molten_amethyst"), builder -> builder
                .require(Items.AMETHYST_SHARD)
                .output(EstrogenFluids.MOLTEN_AMETHYST.get(), getAmount(27000))
                .requiresHeat(HeatCondition.HEATED)
        );

        create(id("balls"), builder -> builder
                .require(Items.SLIME_BALL)
                .output(EstrogenItems.BALLS)
                .output(EstrogenFluids.MOLTEN_SLIME.get(), getAmount(27000))
                .requiresHeat(HeatCondition.HEATED)
        );

        create(id("testosterone_mixture"), builder -> builder
                .require(EstrogenItems.TESTOSTERONE_POWDER)
                .require(Items.COAL)
                .output(EstrogenFluids.TESTOSTERONE_MIXTURE.get(), getAmount(54000))
                .requiresHeat(HeatCondition.HEATED)
        );

        create(id("tinted_glass"), builder -> builder
                .require(Items.GLASS)
                .require(EstrogenFluids.MOLTEN_AMETHYST.get(), getAmount(27000))
                .output(Items.TINTED_GLASS)
        );

        create(id("gender_change_potion"), builder -> builder
            .require(EstrogenFluids.LIQUID_ESTROGEN.get(), 810)
            .require(EstrogenFluids.TESTOSTERONE_MIXTURE.get(), 810)
            .require(Items.POPPY)
            .require(Items.DANDELION)
            .output(EstrogenFluids.GENDER_FLUID.get(), 1620)
        );
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MIXING;
    }
}
