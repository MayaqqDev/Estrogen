package dev.mayaqq.estrogen.datagen.impl.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.FabricRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.ForgeRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;

import java.util.function.UnaryOperator;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenSequencedAssemblyRecipes extends BaseRecipeProvider {

    public EstrogenSequencedAssemblyRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        create("estrogen_patch", b -> b.require(Items.PAPER)
                .transitionTo(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH.get())
                .addOutput(EstrogenItems.ESTROGEN_PATCHES.get().getFullStack(), 120)
                .addOutput(EstrogenItems.ESTROGEN_PILL.get(), 16)
                .addOutput(Items.PAPER, 5)
                .addOutput(Items.SLIME_BALL, 5)
                .addOutput(EstrogenItems.HORSE_URINE_BOTTLE.get(), 4)
                .loops(5)
                .addStep(FillingRecipe::new, rb -> rb.require(EstrogenFluids.MOLTEN_SLIME.get(), getAmount(27000)))
                .addStep(FillingRecipe::new, rb -> rb.require(EstrogenFluids.LIQUID_ESTROGEN.get(), getAmount(27000)))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.PAPER))
        );

        create("uwu", b -> b.require(Items.NETHERITE_INGOT)
                .transitionTo(EstrogenItems.INCOMPLETE_UWU.get())
                .addOutput(EstrogenItems.UWU.get(), 1)
                .loops(10)
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.NETHER_STAR))
                .addStep(PressingRecipe::new, rb -> rb)
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.NETHER_STAR))
                .addStep(PressingRecipe::new, rb -> rb)
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.NETHER_STAR))
                .addStep(PressingRecipe::new, rb -> rb)
        );
    }

    public static EstrogenSequencedAssemblyRecipes buildFabric(FabricDataOutput output) {
        return new EstrogenSequencedAssemblyRecipes(output, new FabricRecipeHelper());
    }

    public static EstrogenSequencedAssemblyRecipes buildForge(FabricDataOutput output) {
        return new EstrogenSequencedAssemblyRecipes(output, new ForgeRecipeHelper());
    }

    protected GeneratedRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
        GeneratedRecipe generatedRecipe =
                c -> transform.apply(new SequencedAssemblyRecipeBuilder(id(name)))
                        .build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.SEQUENCED_ASSEMBLY;
    }
}
