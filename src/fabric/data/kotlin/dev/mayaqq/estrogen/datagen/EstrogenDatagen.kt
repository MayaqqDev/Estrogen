package dev.mayaqq.estrogen.datagen

import dev.mayaqq.estrogen.datagen.impl.tags.EstrogenBlockTags
import dev.mayaqq.estrogen.datagen.impl.tags.EstrogenEntityTags
import dev.mayaqq.estrogen.datagen.impl.tags.EstrogenFluidTags
import dev.mayaqq.estrogen.datagen.impl.tags.EstrogenItemTags
import dev.mayaqq.estrogen.datagen.platform.CommonRecipeHelper
import dev.mayaqq.estrogen.datagen.platform.FabricRecipeHelper
import dev.mayaqq.estrogen.datagen.platform.ForgeRecipeHelper
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import org.apache.commons.io.file.PathUtils
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


object EstrogenDatagen : DataGeneratorEntrypoint {

    private val COMMON_OUTPUT_DIR = required("fabric-api.datagen.output-dir")
    private val FABRIC_OUTPUT_DIR = required("estrogen.datagen.fabric-output-dir")
    private val FORGE_OUTPUT_DIR = required("estrogen.datagen.forge-output-dir")

    override fun onInitializeDataGenerator(fdg: FabricDataGenerator) {
        val commonPath = Paths.get(COMMON_OUTPUT_DIR)
        val fabricPath = Paths.get(FABRIC_OUTPUT_DIR)
        val forgePath = Paths.get(FORGE_OUTPUT_DIR)

        fabricPath.deleteIfExists()
        forgePath.deleteIfExists()

        setupFabric(EstrogenPack.create(fdg, "Estrogen (Fabric)", fabricPath, FabricRecipeHelper), fdg, fabricPath);
        setupForge(EstrogenPack.create(fdg, "Estrogen (Forge)", forgePath, ForgeRecipeHelper), fdg, forgePath);
    }

    fun setupCommon(pack: EstrogenPack) {
        //TODO: pack.addProvider(::EstrogenEntityInteractionRecipes);
        pack.addProvider(::EstrogenBlockTags)
        pack.addProvider(::EstrogenEntityTags)
        pack.addProvider(::EstrogenItemTags)
    }

    fun setupFabric(pack: EstrogenPack, fdg: FabricDataGenerator, path: Path) {
        pack.addProvider(::EstrogenFluidTags)
        setupCommon(EstrogenPack.create(fdg, "Estrogen (Common Fabric)", path, CommonRecipeHelper));
    }

    fun setupForge(pack: EstrogenPack, fdg: FabricDataGenerator, path: Path) {
        pack.addProvider(::EstrogenFluidTags)
        setupCommon(EstrogenPack.create(fdg, "Estrogen (Common Forge)", path, CommonRecipeHelper));
    }

    private inline fun required(property: String) = System.getProperty(property)?:
    throw IllegalArgumentException("No output dir provided with the '$property' property")

    fun Path.deleteIfExists() {
        if (Files.exists(this)) {
            try {
                PathUtils.deleteDirectory(this)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}