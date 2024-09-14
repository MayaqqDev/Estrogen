package dev.mayaqq.estrogen.datagen.impl.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class EstrogenItemApplicationRecipes extends BaseRecipeProvider {

    public EstrogenItemApplicationRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        create(Estrogen.id("dormant_dream_block"), builder -> builder
                .require(Blocks.TINTED_GLASS)
                .require(Items.ENDER_PEARL)
                .output(EstrogenBlocks.DORMANT_DREAM_BLOCK)
        );
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.ITEM_APPLICATION;
    }
}
