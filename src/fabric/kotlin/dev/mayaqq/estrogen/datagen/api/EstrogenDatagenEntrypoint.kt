package dev.mayaqq.estrogen.datagen.api

import dev.mayaqq.estrogen.datagen.api.platform.FabricRecipeHelper
import dev.mayaqq.estrogen.datagen.api.platform.ForgeRecipeHelper
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import org.apache.commons.io.file.PathUtils
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

abstract class EstrogenDatagenEntrypoint(val modid: String) : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fdg: FabricDataGenerator) {
        val commonPath = Paths.get(COMMON_OUTPUT_DIR)
        val fabricPath = Paths.get(FABRIC_OUTPUT_DIR)
        val forgePath = Paths.get(FORGE_OUTPUT_DIR)

        fabricPath.deleteIfExists()
        forgePath.deleteIfExists()

        setupFabricInternal(EstrogenPack.create(fdg, "$modid (Fabric)", fabricPath, FabricRecipeHelper, modid))
        setupForgeInternal(EstrogenPack.create(fdg, "$modid (Neoforge)", forgePath, ForgeRecipeHelper, modid))
    }

    private fun setupFabricInternal(pack: EstrogenPack) {
        setupCommon(pack)
        setupFabric(pack)
    }
    private fun setupForgeInternal(pack: EstrogenPack) {
        setupCommon(pack)
        setupForge(pack)
    }

    abstract fun setupCommon(pack: EstrogenPack)
    abstract fun setupFabric(pack: EstrogenPack)
    abstract fun setupForge(pack: EstrogenPack)

    companion object {
        private val COMMON_OUTPUT_DIR = required("fabric-api.datagen.output-dir")
        private val FABRIC_OUTPUT_DIR = required("estrogen.datagen.fabric-output-dir")
        private val FORGE_OUTPUT_DIR = required("estrogen.datagen.neoforge-output-dir")

        protected inline fun required(property: String) = System.getProperty(property)?:
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
}