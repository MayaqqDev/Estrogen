package dev.mayaqq.estrogen.datagen.recipes.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.item.Items;

import java.util.function.UnaryOperator;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenSequencedAssemblyRecipes extends CreateRecipeProvider {

    GeneratedRecipe
            ESTROGEN_PATCH = create("estrogen_patch", b -> b.require(Items.PAPER)
                .transitionTo(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH)
                .addOutput(EstrogenItems.ESTROGEN_PATCHES, 120)
                .addOutput(EstrogenItems.ESTROGEN_PILL, 16)
                .addOutput(Items.PAPER, 5)
                .addOutput(Items.SLIME_BALL, 5)
                .addOutput(EstrogenItems.HORSE_URINE_BOTTLE, 4)
                .loops(5)
                .addStep(FillingRecipe::new, rb -> rb.require(EstrogenFluids.MOLTEN_SLIME.still(), 27000))
                .addStep(FillingRecipe::new, rb -> rb.require(EstrogenFluids.LIQUID_ESTROGEN.still(), 27000))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.PAPER))
            ),

            UWU = create("uwu", b -> b.require(Items.NETHERITE_INGOT)
                    .transitionTo(EstrogenItems.INCOMPLETE_UWU)
                    .addOutput(EstrogenItems.UWU, 1)
                    .loops(10)
                    .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.NETHER_STAR))
                    .addStep(PressingRecipe::new, rb -> rb)
                    .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.NETHER_STAR))
                    .addStep(PressingRecipe::new, rb -> rb)
                    .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.NETHER_STAR))
                    .addStep(PressingRecipe::new, rb -> rb)
            );


    public EstrogenSequencedAssemblyRecipes(FabricDataGenerator fdg) {
        super(fdg);
    }

    protected GeneratedRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
        GeneratedRecipe generatedRecipe =
                c -> transform.apply(new SequencedAssemblyRecipeBuilder(id(name)))
                        .build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }
}
