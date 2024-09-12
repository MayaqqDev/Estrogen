package dev.mayaqq.estrogen.datagen;

import dev.mayaqq.estrogen.datagen.base.EstrogenPack;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.CommonRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.FabricRecipeHelper;
import dev.mayaqq.estrogen.datagen.base.platform.recipes.ForgeRecipeHelper;
import dev.mayaqq.estrogen.datagen.impl.advancements.EstrogenAdvancements;
import dev.mayaqq.estrogen.datagen.impl.loottables.EstrogenLootTables;
import dev.mayaqq.estrogen.datagen.impl.recipes.create.*;
import dev.mayaqq.estrogen.datagen.impl.recipes.estrogen.EstrogenCentrifugingRecipes;
import dev.mayaqq.estrogen.datagen.impl.recipes.estrogen.EstrogenEntityInteractionRecipe;
import dev.mayaqq.estrogen.datagen.impl.recipes.minecraft.EstrogenCraftingRecipes;
import dev.mayaqq.estrogen.datagen.impl.tags.*;
import dev.mayaqq.estrogen.datagen.impl.translations.EstrogenTranslations;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.apache.commons.io.file.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


public class EstrogenDatagen implements DataGeneratorEntrypoint {

    private static final String COMMON_OUTPUT_DIR = Objects.requireNonNull(
            System.getProperty("fabric-api.datagen.output-dir"),
            "No output dir provided with the 'fabric-api.datagen.output-dir' property"
    );
    private static final String FABRIC_OUTPUT_DIR = Objects.requireNonNull(
            System.getProperty("estrogen.datagen.fabric-output-dir"),
            "No output dir provided with the 'estrogen.datagen.fabric-output-dir' property"
    );
    private static final String FORGE_OUTPUT_DIR = Objects.requireNonNull(
            System.getProperty("estrogen.datagen.forge-output-dir"),
            "No output dir provided with the 'estrogen.datagen.forge-output-dir' property"
    );

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fdg) {
        System.out.println("Estrogen data generation started");
        Path commonOutputPath = Paths.get(COMMON_OUTPUT_DIR);
        Path fabricOutputPath = Paths.get(FABRIC_OUTPUT_DIR);
        Path forgeOutputPath = Paths.get(FORGE_OUTPUT_DIR);
        if (Files.exists(fabricOutputPath)) deleteDirectory(fabricOutputPath);
        if (Files.exists(forgeOutputPath)) deleteDirectory(forgeOutputPath);

        setupCommon(EstrogenPack.create(fdg, "Estrogen (Common)", commonOutputPath, CommonRecipeHelper.INSTANCE));
        setupFabric(EstrogenPack.create(fdg, "Estrogen (Fabric)", fabricOutputPath, FabricRecipeHelper.INSTANCE));
        setupForge(EstrogenPack.create(fdg, "Estrogen (Forge)", forgeOutputPath, ForgeRecipeHelper.INSTANCE));
    }

    private void setupCommon(EstrogenPack pack) {
        pack.addProvider(EstrogenMillingRecipes::new);
        pack.addProvider(EstrogenSandpaperPolishingRecipes::new);
        pack.addProvider(EstrogenEntityInteractionRecipe::new);
        pack.addProvider(EstrogenItemApplicationRecipes::new);
        pack.addProvider(EstrogenDeployingRecipes::new);
        pack.addProvider(EstrogenSplashingRecipes::new);

        pack.addProvider(ModItemTagsProvider::new);
        pack.addProvider(ModBlockTagsProvider::new);
        pack.addProvider(ModEntityTagsProvider::new);

        pack.addProvider(EstrogenAdvancements::new);

        pack.addProvider(EstrogenTranslations::new);

        pack.addProvider(EstrogenLootTables::new);
    }

    private void setupFabric(EstrogenPack pack) {
        pack.addProvider(EstrogenCentrifugingRecipes::new);
        pack.addProvider(EstrogenCraftingRecipes::new);
        pack.addProvider(EstrogenCompactingRecipes::new);
        pack.addProvider(EstrogenEmptyingRecipes::new);
        pack.addProvider(EstrogenFillingRecipes::new);
        pack.addProvider(EstrogenMixingRecipes::new);
        pack.addProvider(EstrogenSequencedAssemblyRecipes::new);

        pack.addProvider(ModFluidTagsProvider::new);
    }

    private void setupForge(EstrogenPack pack) {
        pack.addProvider(EstrogenCentrifugingRecipes::new);
        pack.addProvider(EstrogenCraftingRecipes::new);
        pack.addProvider(EstrogenCompactingRecipes::new);
        pack.addProvider(EstrogenEmptyingRecipes::new);
        pack.addProvider(EstrogenFillingRecipes::new);
        pack.addProvider(EstrogenMixingRecipes::new);
        pack.addProvider(EstrogenSequencedAssemblyRecipes::new);

        pack.addProvider(ModFluidTagsProvider::new);
    }

    public static void deleteDirectory(Path dir) {
        try {
            PathUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
