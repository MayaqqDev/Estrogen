package dev.mayaqq.estrogen.fabric.datagen.recipes.minecraft;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import dev.mayaqq.estrogen.registry.common.EstrogenTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import java.util.function.Consumer;

public class EstrogenCraftingRecipes extends FabricRecipeProvider {

    public EstrogenCraftingRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, EstrogenItems.ESTROGEN_CHIP_COOKIE)
                .requires(EstrogenItems.ESTROGEN_PILL).unlockedBy(FabricRecipeProvider.getHasName(EstrogenItems.ESTROGEN_PILL), FabricRecipeProvider.has(EstrogenItems.ESTROGEN_PILL))
                .requires(AllItems.WHEAT_FLOUR.get())
                .requires(AllItems.BAR_OF_CHOCOLATE.get())
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, EstrogenBlocks.CENTRIFUGE.get(), 1)
                .define('P', AllBlocks.MECHANICAL_PUMP.get())
                .define('T', AllBlocks.FLUID_TANK.get())
                .define('C', EstrogenTags.COPPER_PLATES)
                .define('M', AllItems.PRECISION_MECHANISM.get())
                .pattern("CTC")
                .pattern("PMP")
                .pattern("CTC")
                .unlockedBy(FabricRecipeProvider.getHasName(AllItems.PRECISION_MECHANISM.get()), FabricRecipeProvider.has(AllItems.PRECISION_MECHANISM.get()))
                .save(exporter);
    }
}
