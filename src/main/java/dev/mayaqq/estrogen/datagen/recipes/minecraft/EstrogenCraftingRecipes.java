package dev.mayaqq.estrogen.datagen.recipes.minecraft;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.recipe.RecipeCategory;

import java.util.function.Consumer;

public class EstrogenCraftingRecipes extends FabricRecipeProvider {

    public EstrogenCraftingRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonFactory.create(RecipeCategory.FOOD, EstrogenItems.ESTROGEN_CHIP_COOKIE)
                .ingredient(EstrogenItems.ESTROGEN_PILL).criterion(FabricRecipeProvider.hasItem(EstrogenItems.ESTROGEN_PILL), FabricRecipeProvider.conditionsFromItem(EstrogenItems.ESTROGEN_PILL))
                .ingredient(AllItems.WHEAT_FLOUR.get())
                .ingredient(AllItems.BAR_OF_CHOCOLATE.get())
                .offerTo(exporter);

        ShapedRecipeJsonFactory.create(RecipeCategory.REDSTONE, EstrogenBlocks.CENTRIFUGE.get(), 1)
                .ingredient('P', AllBlocks.MECHANICAL_PUMP.get())
                .ingredient('T', AllBlocks.FLUID_TANK.get())
                .ingredient('C', AllTags.forgeItemTag("copper_plates"))
                .ingredient('M', AllItems.PRECISION_MECHANISM.get())
                .pattern("CTC")
                .pattern("PMP")
                .pattern("CTC")
                .criterion(FabricRecipeProvider.hasItem(AllItems.PRECISION_MECHANISM.get()), FabricRecipeProvider.conditionsFromItem(AllItems.PRECISION_MECHANISM.get()))
                .offerTo(exporter);
    }
}
