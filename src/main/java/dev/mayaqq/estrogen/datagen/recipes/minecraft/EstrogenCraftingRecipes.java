package dev.mayaqq.estrogen.datagen.recipes.minecraft;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;

import java.util.function.Consumer;

public class EstrogenCraftingRecipes extends FabricRecipeProvider {
    public EstrogenCraftingRecipes(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(EstrogenItems.ESTROGEN_CHIP_COOKIE)
                .input(EstrogenItems.ESTROGEN_PILL).criterion(FabricRecipeProvider.hasItem(EstrogenItems.ESTROGEN_PILL), FabricRecipeProvider.conditionsFromItem(EstrogenItems.ESTROGEN_PILL))
                .input(AllItems.WHEAT_FLOUR.get())
                .input(AllItems.BAR_OF_CHOCOLATE.get())
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(EstrogenBlocks.CENTRIFUGE.get(), 1)
                .input('P', AllBlocks.MECHANICAL_PUMP.get())
                .input('T', AllBlocks.FLUID_TANK.get())
                .input('C', AllTags.forgeItemTag("copper_plates"))
                .input('M', AllItems.PRECISION_MECHANISM.get())
                .pattern("CTC")
                .pattern("PMP")
                .pattern("CTC")
                .criterion(FabricRecipeProvider.hasItem(AllItems.PRECISION_MECHANISM.get()), FabricRecipeProvider.conditionsFromItem(AllItems.PRECISION_MECHANISM.get()))
                .offerTo(exporter);
    }
}
