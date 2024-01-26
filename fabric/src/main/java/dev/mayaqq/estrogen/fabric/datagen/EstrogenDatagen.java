package dev.mayaqq.estrogen.fabric.datagen;

import dev.mayaqq.estrogen.fabric.datagen.loottables.EstrogenLootTables;
import dev.mayaqq.estrogen.fabric.datagen.recipes.create.*;
import dev.mayaqq.estrogen.fabric.datagen.recipes.estrogen.EstrogenCentrifugingRecipes;
import dev.mayaqq.estrogen.fabric.datagen.recipes.minecraft.EstrogenCraftingRecipes;
import dev.mayaqq.estrogen.fabric.datagen.tags.EstrogenTags;
import dev.mayaqq.estrogen.fabric.datagen.translations.EstrogenTranslations;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class EstrogenDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator pack) {
        // recipes
        pack.addProvider(EstrogenCentrifugingRecipes::buildFabric);
        pack.addProvider(EstrogenCentrifugingRecipes::buildForge);
        pack.addProvider(EstrogenCraftingRecipes::new);
        pack.addProvider(EstrogenCompactingRecipes::buildFabric);
        pack.addProvider(EstrogenCompactingRecipes::buildForge);
        pack.addProvider(EstrogenEmptyingRecipes::buildFabric);
        pack.addProvider(EstrogenEmptyingRecipes::buildForge);
        pack.addProvider(EstrogenFillingRecipes::buildFabric);
        pack.addProvider(EstrogenFillingRecipes::buildForge);
        pack.addProvider(EstrogenMillingRecipes::new);
        pack.addProvider(EstrogenSandpaperPolishingRecipes::new);
        pack.addProvider(EstrogenMixingRecipes::buildFabric);
        pack.addProvider(EstrogenMixingRecipes::buildForge);
        pack.addProvider(EstrogenSequencedAssemblyRecipes::buildFabric);
        pack.addProvider(EstrogenSequencedAssemblyRecipes::buildForge);

        // tags
        pack.addProvider(EstrogenTags.ItemTags::new);
        pack.addProvider(EstrogenTags.BlockTags::new);
        pack.addProvider(EstrogenTags.FluidTags::new);
        pack.addProvider(EstrogenTags.EntityTypeTags::new);

        // Lang
        pack.addProvider(EstrogenTranslations.EnUs::new);
        pack.addProvider(EstrogenTranslations.HuHu::new);
        pack.addProvider(EstrogenTranslations.CsCz::new);
        pack.addProvider(EstrogenTranslations.PtBr::new);

        // Loot Tables
        pack.addProvider(EstrogenLootTables::new);
    }
}
