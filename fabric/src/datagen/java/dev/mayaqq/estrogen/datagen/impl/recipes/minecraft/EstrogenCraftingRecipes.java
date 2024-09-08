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
        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, EstrogenItems.ESTROGEN_CHIP_COOKIE.get())
                .requires(EstrogenItems.ESTROGEN_PILL.get())
                .unlockedBy(getHasName(EstrogenItems.ESTROGEN_PILL.get()), has(EstrogenItems.ESTROGEN_PILL.get()))
                .requires(AllItems.WHEAT_FLOUR.get())
                .requires(AllItems.BAR_OF_CHOCOLATE.get())
        );

        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.ESTROGEN_PILL_BLOCK.asItem())
                .requires(EstrogenItems.ESTROGEN_PILL.get(), 9)
                .unlockedBy(getHasName(EstrogenItems.ESTROGEN_PILL.get()), has(EstrogenItems.ESTROGEN_PILL.get()))
        );

        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, EstrogenItems.ESTROGEN_PILL.get(), 9)
                .requires(EstrogenBlocks.ESTROGEN_PILL_BLOCK.asItem())
                .unlockedBy(getHasName(EstrogenBlocks.ESTROGEN_PILL_BLOCK.get()), has(EstrogenBlocks.ESTROGEN_PILL_BLOCK.asItem()))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, EstrogenBlocks.CENTRIFUGE.get(), 1)
                .define('P', AllBlocks.MECHANICAL_PUMP.get())
                .define('T', AllBlocks.FLUID_TANK.get())
                .define('C', getCommonTag("copper_plates"))
                .define('M', AllItems.PRECISION_MECHANISM.get())
                .pattern("CTC")
                .pattern("PMP")
                .pattern("CTC")
                .unlockedBy(getHasName(AllItems.PRECISION_MECHANISM.get()), has(AllItems.PRECISION_MECHANISM.get()))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.COOKIE_JAR.get(), 1)
                .define('G', getCommonTag("glass_panes"))
                .define('Z', getCommonTag("zinc_nuggets"))
                .pattern("GZG")
                .pattern("G G")
                .pattern("GGG")
                .unlockedBy(getHasName(AllItems.ZINC_NUGGET.get()), has(AllItems.ZINC_NUGGET.get()))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EstrogenItems.THIGH_HIGHS.get(), 1)
                .define('F', EstrogenItems.MOTH_FUZZ.get())
                .pattern("FFF")
                .pattern("F F")
                .pattern("F F")
                .unlockedBy(getHasName(EstrogenItems.MOTH_FUZZ.get()), has(EstrogenItems.MOTH_FUZZ.get()))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.MOTH_WOOL.asItem(), 1)
                .define('F', EstrogenItems.MOTH_FUZZ.get())
                .pattern("FF")
                .pattern("FF")
                .unlockedBy(getHasName(EstrogenItems.MOTH_FUZZ.get()), has(EstrogenItems.MOTH_FUZZ.get()))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.QUILTED_MOTH_WOOL.asItem(), 4)
                .define('F', EstrogenBlocks.MOTH_WOOL.asItem())
                .pattern("FF")
                .pattern("FF")
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL.asItem()), has(EstrogenBlocks.MOTH_WOOL.asItem()))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.MOTH_WOOL_CARPET.asItem(), 3)
                .define('F', EstrogenBlocks.MOTH_WOOL.asItem())
                .pattern("FF")
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL.asItem()), has(EstrogenBlocks.MOTH_WOOL.asItem()))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.QUILTED_MOTH_WOOL_CARPET.asItem(), 3)
                .define('F', EstrogenBlocks.QUILTED_MOTH_WOOL.asItem())
                .pattern("FF")
                .unlockedBy(getHasName(EstrogenBlocks.QUILTED_MOTH_WOOL.asItem()), has(EstrogenBlocks.QUILTED_MOTH_WOOL.asItem()))
        );

        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, EstrogenBlocks.MOTH_SEAT.asItem())
                .requires(EstrogenBlocks.QUILTED_MOTH_WOOL.asItem(), 1)
                .requires(ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL.asItem()), has(EstrogenBlocks.MOTH_WOOL.asItem())
        ));

        add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EstrogenItems.MOTH_FUZZ.get(), 4)
                .requires(EstrogenBlocks.MOTH_WOOL.asItem())
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL.asItem()), has(EstrogenBlocks.MOTH_WOOL.asItem())
        ));

        add(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, EstrogenBlocks.MOTH_BED.get(), 1)
                .define('M', EstrogenBlocks.MOTH_WOOL.asItem())
                .define('W', ItemTags.PLANKS)
                .pattern("MMM")
                .pattern("WWW")
                .unlockedBy(getHasName(EstrogenBlocks.MOTH_WOOL.asItem()), has(EstrogenBlocks.MOTH_WOOL.asItem()))
        );

        add(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, EstrogenBlocks.QUILTED_MOTH_BED.get(), 1)
                .define('M', EstrogenBlocks.QUILTED_MOTH_WOOL.asItem())
                .define('W', ItemTags.PLANKS)
                .pattern("MMM")
                .pattern("WWW")
                .unlockedBy(getHasName(EstrogenBlocks.QUILTED_MOTH_WOOL.asItem()), has(EstrogenBlocks.QUILTED_MOTH_WOOL.asItem()))
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
