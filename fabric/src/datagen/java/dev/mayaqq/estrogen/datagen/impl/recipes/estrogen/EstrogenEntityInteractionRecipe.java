package dev.mayaqq.estrogen.datagen.impl.recipes.estrogen;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import dev.mayaqq.estrogen.datagen.base.recipes.BaseRecipeProvider;
import dev.mayaqq.estrogen.datagen.base.recipes.EstrogenEntityInteractionRecipeBuilder;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import dev.mayaqq.estrogen.registry.recipes.objects.EntityObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class EstrogenEntityInteractionRecipe extends BaseRecipeProvider {

    public EstrogenEntityInteractionRecipe(FabricDataOutput output, PlatformRecipeHelper helper) {
        super(output, helper);
    }

    @Override
    protected void init() {
        add(EstrogenEntityInteractionRecipeBuilder.create(
                Items.GLASS_BOTTLE,
                EstrogenItems.HORSE_URINE_BOTTLE.get(),
                1,
                EntityObject.of(EstrogenTags.Entities.URINE_GIVING),
                new ResourceLocation("minecraft:item.bottle.fill"),
                true
        ));
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return EntityInteractionRecipe.getRecipeTypeInfo();
    }
}
