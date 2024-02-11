package dev.mayaqq.estrogen.datagen;

import dev.mayaqq.estrogen.datagen.loottables.EstrogenLootTables;
import dev.mayaqq.estrogen.datagen.recipes.create.*;
import dev.mayaqq.estrogen.datagen.recipes.estrogen.EstrogenCentrifugingRecipes;
import dev.mayaqq.estrogen.datagen.recipes.minecraft.EstrogenCraftingRecipes;
import dev.mayaqq.estrogen.datagen.tags.EstrogenTagsGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import dev.mayaqq.estrogen.datagen.translations.*;

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
        pack.addProvider(EstrogenTagsGen.ItemTags::new);
        pack.addProvider(EstrogenTagsGen.BlockTags::new);
        pack.addProvider(EstrogenTagsGen.FluidTags::new);
        pack.addProvider(EstrogenTagsGen.EntityTypeTags::new);

        // Lang
        pack.addProvider(EnUs::new);
        pack.addProvider(HuHu::new);
        pack.addProvider(CsCz::new);
        pack.addProvider(PtBr::new);
        pack.addProvider(FrFr::new);
        Hispanic.addAll(pack);

        // Loot Tables
        pack.addProvider(EstrogenLootTables::new);
    }
}
