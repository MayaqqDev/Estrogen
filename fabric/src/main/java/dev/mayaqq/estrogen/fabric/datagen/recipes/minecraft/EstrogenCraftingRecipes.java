package dev.mayaqq.estrogen.fabric.datagen.recipes.minecraft;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.utility.DyeHelper;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeFabricImpl;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeForgeImpl;
import dev.mayaqq.estrogen.fabric.datagen.recipes.EstrogenRecipeInterface;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;

import java.util.function.Consumer;

public class EstrogenCraftingRecipes<T extends EstrogenRecipeInterface> extends FabricRecipeProvider {

    private T t;

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, EstrogenItems.ESTROGEN_CHIP_COOKIE.get())
                .requires(EstrogenItems.ESTROGEN_PILL.get()).unlockedBy(FabricRecipeProvider.getHasName(EstrogenItems.ESTROGEN_PILL.get()), FabricRecipeProvider.has(EstrogenItems.ESTROGEN_PILL.get()))
                .requires(AllItems.WHEAT_FLOUR.get())
                .requires(AllItems.BAR_OF_CHOCOLATE.get())
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, EstrogenItems.ESTROGEN_PILL_BLOCK.get())
                        .requires(EstrogenItems.ESTROGEN_PILL.get(), 9)
                        .unlockedBy(FabricRecipeProvider.getHasName(EstrogenItems.ESTROGEN_PILL.get()), FabricRecipeProvider.has(EstrogenItems.ESTROGEN_PILL.get()))
                        .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, EstrogenItems.ESTROGEN_PILL.get(), 9)
                        .requires(EstrogenItems.ESTROGEN_PILL_BLOCK.get())
                        .unlockedBy(FabricRecipeProvider.getHasName(EstrogenItems.ESTROGEN_PILL_BLOCK.get()), FabricRecipeProvider.has(EstrogenItems.ESTROGEN_PILL_BLOCK.get()))
                        .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, EstrogenBlocks.CENTRIFUGE.get(), 1)
                .define('P', AllBlocks.MECHANICAL_PUMP.get())
                .define('T', AllBlocks.FLUID_TANK.get())
                .define('C', t.getCommonTag("copper_plates"))
                .define('M', AllItems.PRECISION_MECHANISM.get())
                .pattern("CTC")
                .pattern("PMP")
                .pattern("CTC")
                .unlockedBy(FabricRecipeProvider.getHasName(AllItems.PRECISION_MECHANISM.get()), FabricRecipeProvider.has(AllItems.PRECISION_MECHANISM.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.COOKIE_JAR.get(), 1)
                .define('G', t.getCommonTag("glass_panes"))
                .define('Z', t.getCommonTag("zinc_nuggets"))
                .pattern("GZG")
                .pattern("G G")
                .pattern("GGG")
                .unlockedBy(FabricRecipeProvider.getHasName(AllItems.ZINC_NUGGET.get()), FabricRecipeProvider.has(AllItems.ZINC_NUGGET.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EstrogenItems.THIGH_HIGHS.get(), 1)
                .define('F', EstrogenItems.MOTH_FUZZ.get())
                .pattern("FFF")
                .pattern("F F")
                .pattern("F F")
                .unlockedBy(FabricRecipeProvider.getHasName(EstrogenItems.MOTH_FUZZ.get()), FabricRecipeProvider.has(EstrogenItems.MOTH_FUZZ.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenItems.MOTH_WOOL.get(), 1)
                .define('F', EstrogenItems.MOTH_FUZZ.get())
                .pattern("FF")
                .pattern("FF")
                .unlockedBy(FabricRecipeProvider.getHasName(EstrogenItems.MOTH_FUZZ.get()), FabricRecipeProvider.has(EstrogenItems.MOTH_FUZZ.get()))
                .save(exporter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, EstrogenItems.MOTH_SEAT.get())
                .requires(EstrogenItems.MOTH_WOOL.get(), 1)
                .requires(ItemTags.WOODEN_SLABS)
                .unlockedBy(FabricRecipeProvider.getHasName(EstrogenItems.MOTH_WOOL.get()), FabricRecipeProvider.has(EstrogenItems.MOTH_WOOL.get()))
                .save(exporter);

        SpecialRecipeBuilder.special(EstrogenRecipes.THIGH_HIGH_DYE_SERIALIZER.get()).save(exporter, "estrogen:thigh_high_dye");
    }

    public EstrogenCraftingRecipes(FabricDataOutput output, T t) {
        super(output);
        this.t = t;
    }

    public static EstrogenCraftingRecipes<?> buildFabric(FabricDataOutput output) {
        return new EstrogenCraftingRecipes<>(output, new EstrogenRecipeFabricImpl());
    }

    public static EstrogenCraftingRecipes<?> buildForge(FabricDataOutput output) {
        return new EstrogenCraftingRecipes<>(output, new EstrogenRecipeForgeImpl());
    }

    @Override
    public String getName() {
        return t.getName(super.getName());
    }
}
