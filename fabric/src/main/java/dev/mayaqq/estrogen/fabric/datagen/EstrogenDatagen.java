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
        pack.addProvider((Factory<?>) EstrogenCentrifugingRecipes::buildFabric);
        pack.addProvider((Factory<?>) EstrogenCentrifugingRecipes::buildForge);
        pack.addProvider(EstrogenCraftingRecipes::new);
        pack.addProvider((Factory<?>) EstrogenCompactingRecipes::buildFabric);
        pack.addProvider((Factory<?>) EstrogenCompactingRecipes::buildForge);
        pack.addProvider((Factory<?>) EstrogenEmptyingRecipes::buildFabric);
        pack.addProvider((Factory<?>) EstrogenEmptyingRecipes::buildForge);
        pack.addProvider((Factory<?>) EstrogenFillingRecipes::buildFabric);
        pack.addProvider((Factory<?>) EstrogenFillingRecipes::buildForge);
        pack.addProvider(EstrogenMillingRecipes::new);
        pack.addProvider(EstrogenSandpaperPolishingRecipes::new);
        pack.addProvider((Factory<?>) EstrogenMixingRecipes::buildFabric);
        pack.addProvider((Factory<?>) EstrogenMixingRecipes::buildForge);
        pack.addProvider((Factory<?>) EstrogenSequencedAssemblyRecipes::buildFabric);
        pack.addProvider((Factory<?>) EstrogenSequencedAssemblyRecipes::buildForge);

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
