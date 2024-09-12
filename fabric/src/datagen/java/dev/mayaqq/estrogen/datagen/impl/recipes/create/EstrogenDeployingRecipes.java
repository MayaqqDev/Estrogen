package dev.mayaqq.estrogen.datagen.impl.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;

public class EstrogenDeployingRecipes extends BaseRecipeProvider {

    public EstrogenDeployingRecipes(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        create(Estrogen.id("moth_elytra"), builder -> builder
                .require(Items.ELYTRA)
                .require(EstrogenItems.MOTH_FUZZ)
                .output(EstrogenItems.MOTH_ELYTRA)
        );
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.DEPLOYING;
    }
}
