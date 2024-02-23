package dev.mayaqq.estrogen.datagen.recipes.create;

import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import dev.mayaqq.estrogen.datagen.recipes.EstrogenRecipeFabricImpl;
import dev.mayaqq.estrogen.datagen.recipes.EstrogenRecipeForgeImpl;
import dev.mayaqq.estrogen.datagen.recipes.EstrogenRecipeInterface;
import dev.mayaqq.estrogen.registry.EstrogenCreateItems;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.function.UnaryOperator;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenSequencedAssemblyRecipes<T extends EstrogenRecipeInterface> extends CreateRecipeProvider {

    private T t;
    GeneratedRecipe
        ESTROGEN_PATCH = create("estrogen_patch", b -> b.require(Items.PAPER)
            .transitionTo(EstrogenCreateItems.INCOMPLETE_ESTROGEN_PATCH)
            .addOutput(EstrogenCreateItems.ESTROGEN_PATCHES.get().getFullStack(), 120)
            .addOutput(EstrogenCreateItems.ESTROGEN_PILL, 16)
            .addOutput(Items.PAPER, 5)
            .addOutput(Items.SLIME_BALL, 5)
            .addOutput(EstrogenCreateItems.HORSE_URINE_BOTTLE, 4)
            .loops(5)
            .addStep(FillingRecipe::new, rb -> rb.require(EstrogenFluids.MOLTEN_SLIME.get(), t.getAmount(27000)))
            .addStep(FillingRecipe::new, rb -> rb.require(EstrogenFluids.LIQUID_ESTROGEN.get(), t.getAmount(27000)))
            .addStep(DeployerApplicationRecipe::new, rb -> rb.require(Items.PAPER))
    ),

        UWU = create("uwu", b -> b.require(Items.NETHERITE_INGOT)
            .transitionTo(EstrogenCreateItems.INCOMPLETE_UWU)
            .addOutput(EstrogenCreateItems.UWU, 1)
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
    protected ResourceLocation getRecipeIdentifier(ResourceLocation identifier) {
        return t.getRecipeIdentifier(identifier);
    }

    @Override
    public String getName() {
        return t.getName("Estrogen's Sequenced Assembly Recipes");
    }
}
