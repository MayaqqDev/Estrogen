package dev.mayaqq.estrogen.fabric.datagen;

import dev.mayaqq.estrogen.fabric.datagen.advancements.EstrogenAdvancements;
import dev.mayaqq.estrogen.fabric.datagen.loottables.EstrogenLootTables;
import dev.mayaqq.estrogen.fabric.datagen.recipes.create.*;
import dev.mayaqq.estrogen.fabric.datagen.recipes.estrogen.EstrogenCentrifugingRecipes;
import dev.mayaqq.estrogen.fabric.datagen.recipes.estrogen.EstrogenEntityInteractionRecipe;
import dev.mayaqq.estrogen.fabric.datagen.recipes.minecraft.EstrogenCraftingRecipes;
import dev.mayaqq.estrogen.fabric.datagen.tags.EstrogenTagsGen;
import dev.mayaqq.estrogen.fabric.datagen.translations.EstrogenTranslations;
import dev.mayaqq.estrogen.fabric.mixin.PackInvoker;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack.Factory;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


public class EstrogenDatagen implements DataGeneratorEntrypoint {
    private static final boolean STRICT_VALIDATION = System.getProperty("fabric-api.datagen.strict-validation") != null;
    private static final String FABRIC_OUTPUT_DIR = Objects.requireNonNull(System.getProperty("estrogen.datagen.fabric-output-dir", "No output dir provided with the 'estrogen.datagen.fabric-output-dir' property"));
    private static final String FORGE_OUTPUT_DIR = Objects.requireNonNull(System.getProperty("estrogen.datagen.forge-output-dir", "No output dir provided with the 'estrogen.datagen.forge-output-dir' property"));

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fdg) {
        FabricDataGenerator.Pack common = fdg.createPack();
        Path fabricOutputPath = Paths.get(FABRIC_OUTPUT_DIR);
        Path forgeOutputPath = Paths.get(FORGE_OUTPUT_DIR);
        FabricDataGenerator.Pack fabric = PackInvoker.create(fdg, true, "Estrogen (Fabric)", new FabricDataOutput(fdg.getModContainer(), fabricOutputPath, STRICT_VALIDATION));
        FabricDataGenerator.Pack forge = PackInvoker.create(fdg, true, "Estrogen (Forge)", new FabricDataOutput(fdg.getModContainer(), forgeOutputPath, STRICT_VALIDATION));

        // recipes
        fabric.addProvider((Factory<?>) EstrogenCentrifugingRecipes::buildFabric);
        forge.addProvider((Factory<?>) EstrogenCentrifugingRecipes::buildForge);
        fabric.addProvider((Factory<?>) EstrogenCraftingRecipes::buildFabric);
        forge.addProvider((Factory<?>) EstrogenCraftingRecipes::buildForge);
        fabric.addProvider((Factory<?>) EstrogenCompactingRecipes::buildFabric);
        forge.addProvider((Factory<?>) EstrogenCompactingRecipes::buildForge);
        fabric.addProvider((Factory<?>) EstrogenEmptyingRecipes::buildFabric);
        forge.addProvider((Factory<?>) EstrogenEmptyingRecipes::buildForge);
        fabric.addProvider((Factory<?>) EstrogenFillingRecipes::buildFabric);
        forge.addProvider((Factory<?>) EstrogenFillingRecipes::buildForge);
        common.addProvider(EstrogenMillingRecipes::new);
        common.addProvider(EstrogenSandpaperPolishingRecipes::new);
        fabric.addProvider((Factory<?>) EstrogenMixingRecipes::buildFabric);
        forge.addProvider((Factory<?>) EstrogenMixingRecipes::buildForge);
        fabric.addProvider((Factory<?>) EstrogenSequencedAssemblyRecipes::buildFabric);
        forge.addProvider((Factory<?>) EstrogenSequencedAssemblyRecipes::buildForge);
        common.addProvider(EstrogenEntityInteractionRecipe::new);
        common.addProvider(EstrogenItemApplicationRecipes::new);
        common.addProvider(EstrogenDeployingRecipes::new);

        // tags
        common.addProvider(EstrogenTagsGen.ItemTags::new);
        common.addProvider(EstrogenTagsGen.BlockTags::new);
        fabric.addProvider(EstrogenTagsGen.FluidTags::buildFabric);
        forge.addProvider(EstrogenTagsGen.FluidTags::buildForge);
        common.addProvider(EstrogenTagsGen.EntityTypeTags::new);

        // Advancements
        common.addProvider(EstrogenAdvancements::new);

        // Lang
        common.addProvider(EstrogenTranslations::new);

        // Loot Tables
        common.addProvider(EstrogenLootTables::new);
    }
}
