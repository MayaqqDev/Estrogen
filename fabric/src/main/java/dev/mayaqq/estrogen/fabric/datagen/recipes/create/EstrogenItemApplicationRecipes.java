package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class EstrogenItemApplicationRecipes extends ProcessingRecipeGen {

    GeneratedRecipe DORMANT_DREAM_BLOCK = create(Estrogen.id("dormant_dream_block"), b -> b
            .require(Blocks.TINTED_GLASS)
            .require(Items.ENDER_PEARL)
            .output(EstrogenBlocks.DORMANT_DREAM_BLOCK.get()));




    public EstrogenItemApplicationRecipes(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.ITEM_APPLICATION;
    }

    @Override
    public String getName() {
        return "Estrogen Item Application Recipes";
    }
}
