package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.item.Items;

public class EstrogenDeployingRecipes extends ProcessingRecipeGen {

    GeneratedRecipe MOTH_ELYTRA = create(Estrogen.id("moth_elytra"), b -> b
            .require(Items.ELYTRA)
            .require(EstrogenItems.MOTH_FUZZ.get())
            .output(EstrogenItems.MOTH_ELYTRA.get()));

    public EstrogenDeployingRecipes(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.DEPLOYING;
    }
}
