package dev.mayaqq.estrogen.datagen.impl.recipes.minecraft;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.tags.ItemTags;

public class EstrogenCraftingRecipes extends BaseRecipeProvider {

    public EstrogenCraftingRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, EstrogenItems.ESTROGEN_CHIP_COOKIE)
                .requires(EstrogenItems.ESTROGEN_PILL)
                .unlockedBy(getHasName(EstrogenItems.ESTROGEN_PILL), has(EstrogenItems.ESTROGEN_PILL))
                .requires(AllItems.WHEAT_FLOUR)
                .requires(AllItems.BAR_OF_CHOCOLATE)
        );

        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.ESTROGEN_PILL_BLOCK)
                .requires(EstrogenItems.ESTROGEN_PILL, 9)
                .unlockedBy(getHasName(EstrogenItems.ESTROGEN_PILL), has(EstrogenItems.ESTROGEN_PILL))
        );

        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, EstrogenItems.ESTROGEN_PILL, 9)
                .requires(EstrogenBlocks.ESTROGEN_PILL_BLOCK)
                .unlockedBy(getHasName(EstrogenBlocks.ESTROGEN_PILL_BLOCK), has(EstrogenBlocks.ESTROGEN_PILL_BLOCK))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, EstrogenBlocks.CENTRIFUGE, 1)
                .define('P', AllBlocks.MECHANICAL_PUMP)
                .define('T', AllBlocks.FLUID_TANK)
                .define('C', getCommonTag("copper_plates"))
                .define('M', AllItems.PRECISION_MECHANISM)
                .pattern("CTC")
                .pattern("PMP")
                .pattern("CTC")
                .unlockedBy(getHasName(AllItems.PRECISION_MECHANISM), has(AllItems.PRECISION_MECHANISM))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.COOKIE_JAR, 1)
                .define('G', getCommonTag("glass_panes"))
                .define('Z', getCommonTag("zinc_nuggets"))
                .pattern("GZG")
                .pattern("G G")
                .pattern("GGG")
                .unlockedBy(getHasName(AllItems.ZINC_NUGGET), has(AllItems.ZINC_NUGGET))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EstrogenItems.THIGH_HIGHS, 1)
                .define('F', EstrogenItems.MOTH_FUZZ)
                .pattern("FFF")
                .pattern("F F")
                .pattern("F F")
                .unlockedBy(getHasName(EstrogenItems.MOTH_FUZZ), has(EstrogenItems.MOTH_FUZZ))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.MOTH_WOOL, 1)
                .define('F', EstrogenItems.MOTH_FUZZ)
                .pattern("FF")
                .pattern("FF")
                .unlockedBy(getHasName(EstrogenItems.MOTH_FUZZ), has(EstrogenItems.MOTH_FUZZ))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.QUILTED_MOTH_WOOL, 4)
                .define('F', EstrogenBlocks.MOTH_WOOL)
                .pattern("FF")
                .pattern("FF")
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL), has(EstrogenBlocks.MOTH_WOOL))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.MOTH_WOOL_CARPET, 3)
                .define('F', EstrogenBlocks.MOTH_WOOL)
                .pattern("FF")
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL), has(EstrogenBlocks.MOTH_WOOL))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.QUILTED_MOTH_WOOL_CARPET, 3)
                .define('F', EstrogenBlocks.QUILTED_MOTH_WOOL)
                .pattern("FF")
                .unlockedBy(getHasName(EstrogenBlocks.QUILTED_MOTH_WOOL), has(EstrogenBlocks.QUILTED_MOTH_WOOL))
        );

        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.MOTH_SEAT)
                .requires(EstrogenBlocks.QUILTED_MOTH_WOOL, 1)
                .requires(ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL), has(EstrogenBlocks.MOTH_WOOL)
        ));

        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EstrogenItems.MOTH_FUZZ, 4)
                .requires(EstrogenBlocks.MOTH_WOOL)
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL), has(EstrogenBlocks.MOTH_WOOL)
        ));

        add(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, EstrogenBlocks.MOTH_BED, 1)
                .define('M', EstrogenBlocks.MOTH_WOOL)
                .define('W', ItemTags.PLANKS)
                .pattern("MMM")
                .pattern("WWW")
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL), has(EstrogenBlocks.MOTH_WOOL))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, EstrogenBlocks.QUILTED_MOTH_BED, 1)
                .define('M', EstrogenBlocks.QUILTED_MOTH_WOOL)
                .define('W', ItemTags.PLANKS)
                .pattern("MMM")
                .pattern("WWW")
                .unlockedBy(getHasName(EstrogenBlocks.QUILTED_MOTH_WOOL), has(EstrogenBlocks.QUILTED_MOTH_WOOL))
        );

        add(exporter -> SpecialRecipeBuilder.special(EstrogenRecipes.THIGH_HIGH_DYE_SERIALIZER.get())
                .save(exporter, "estrogen:thigh_high_dye")
        );
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return null;
    }

    @Override
    public String getRecipeName() {
        return "crafting";
    }
}
