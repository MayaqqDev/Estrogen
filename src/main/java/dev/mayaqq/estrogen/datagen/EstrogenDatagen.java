package dev.mayaqq.estrogen.datagen;

import dev.mayaqq.estrogen.datagen.recipes.create.*;
import dev.mayaqq.estrogen.datagen.recipes.estrogen.EstrogenCentrifugingRecipes;
import dev.mayaqq.estrogen.datagen.recipes.minecraft.EstrogenCraftingRecipes;
import dev.mayaqq.estrogen.datagen.tags.EstrogenTags;
import dev.mayaqq.estrogen.datagen.translations.EstrogenTranslations;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;


public class EstrogenDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fdg) {
        // recipes
        fdg.addProvider(EstrogenCentrifugingRecipes::new);
        fdg.addProvider(EstrogenCraftingRecipes::new);
        fdg.addProvider(EstrogenCompactingRecipes::new);
        fdg.addProvider(EstrogenEmptyingRecipes::new);
        fdg.addProvider(EstrogenFillingRecipes::new);
        fdg.addProvider(EstrogenMillingRecipes::new);
        fdg.addProvider(EstrogenSandpaperPolishingRecipes::new);
        fdg.addProvider(EstrogenMixingRecipes::new);
        fdg.addProvider(EstrogenSequencedAssemblyRecipes::new);

        // tags
        fdg.addProvider(EstrogenTags.ItemTags::new);
        fdg.addProvider(EstrogenTags.BlockTags::new);
        fdg.addProvider(EstrogenTags.FluidTags::new);
        fdg.addProvider(EstrogenTags.EntityTypeTags::new);

        // Lang
        fdg.addProvider(EstrogenTranslations.EnUs::new);
        fdg.addProvider(EstrogenTranslations.FrFr::new);
    }
}
