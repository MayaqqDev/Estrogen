package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeFabricImpl;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeForgeImpl;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeInterface;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;

import java.util.function.UnaryOperator;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenSequencedAssemblyRecipes<T extends EstrogenRecipeInterface> extends CreateRecipeProvider {

    private T t;
    GeneratedRecipe
        ESTROGEN_PATCH = create("estrogen_patch", b -> b.require(Items.PAPER)
            .transitionTo(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH.get())
            .addOutput(EstrogenItems.ESTROGEN_PATCHES.get().getFullStack(), 120)
            .addOutput(EstrogenItems.ESTROGEN_PILL.get(), 16)
            .addOutput(Items.PAPER, 5)
            .addOutput(Items.SLIME_BALL, 5)
            .addOutput(EstrogenItems.HORSE_URINE_BOTTLE.get(), 4)
            .loops(5)
            .addStep(FillingRecipe::new, rb -> rb.require(EstrogenFluids.MOLTEN_SLIME.get(), t.getAmount(27000)))
            .addStep(FillingRecipe::new, rb -> rb.require(EstrogenFluids.LIQUID_ESTROGEN.get(), t.getAmount(27000)))
            .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.PAPER))
    ),

        UWU = create("uwu", b -> b.require(Items.NETHERITE_INGOT)
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


    public EstrogenSequencedAssemblyRecipes(FabricDataOutput output, T t) {
        super(output);
        this.t = t;
    }

    public static EstrogenSequencedAssemblyRecipes<?> buildFabric(FabricDataOutput output) {
        return new EstrogenSequencedAssemblyRecipes<>(output, new EstrogenRecipeFabricImpl());
    }

    public static EstrogenSequencedAssemblyRecipes<?> buildForge(FabricDataOutput output) {
        return new EstrogenSequencedAssemblyRecipes<>(output, new EstrogenRecipeForgeImpl());
    }

    protected GeneratedRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
        GeneratedRecipe generatedRecipe =
                c -> transform.apply(new SequencedAssemblyRecipeBuilder(id(name)))
                        .build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }

    @Override
    public String getName() {
        return t.getName("Estrogen's Sequenced Assembly Recipes");
    }
}
