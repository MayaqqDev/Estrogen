package dev.mayaqq.estrogen.datagen;

import dev.mayaqq.estrogen.datagen.loottables.EstrogenLootTables;
import dev.mayaqq.estrogen.datagen.recipes.create.*;
import dev.mayaqq.estrogen.datagen.recipes.estrogen.EstrogenCentrifugingRecipes;
import dev.mayaqq.estrogen.datagen.recipes.minecraft.EstrogenCraftingRecipes;
import dev.mayaqq.estrogen.datagen.tags.EstrogenTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack.Factory;
import dev.mayaqq.estrogen.datagen.translations.languages.*;


public class EstrogenDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fdg) {
        FabricDataGenerator.Pack pack = fdg.createPack();
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
        pack.addProvider(CsCz::new);
        pack.addProvider(EnUs::new);
        pack.addProvider(FrFr::new);
        pack.addProvider(HuHu::new);
        pack.addProvider(PtBr::new);
        Hispanic.addAll(pack);

        // Loot Tables
        pack.addProvider(EstrogenLootTables::new);
    }
}
