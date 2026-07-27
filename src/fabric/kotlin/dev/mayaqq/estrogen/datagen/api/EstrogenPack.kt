package dev.mayaqq.estrogen.datagen.api

import dev.mayaqq.estrogen.datagen.api.platform.PlatformRecipeHelper
import dev.mayaqq.estrogen.fabric.mixins.PackInvoker
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import net.minecraft.core.HolderLookup
import net.minecraft.data.DataProvider
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

class EstrogenPack(
    val pack: FabricDataGenerator.Pack,
    val helper: PlatformRecipeHelper
) {

    companion object {
        fun create(fdg: FabricDataGenerator, name: String, output: Path, helper: PlatformRecipeHelper, modid: String): EstrogenPack {
            val mod: ModContainer = FabricLoader.getInstance().getModContainer(modid).orElseThrow()
            return EstrogenPack(
                PackInvoker.create(
                    fdg, true,
                    name,
                    FabricDataOutput(mod, output, fdg.isStrictValidationEnabled)
                ),
                helper
            )
        }
    }


    fun <T : DataProvider> addProvider(factory: FabricDataGenerator.Pack.Factory<T>) {
        pack.addProvider(factory)
    }

    fun <T : DataProvider> addProvider(factory: FabricDataGenerator.Pack.RegistryDependentFactory<T>) {
        pack.addProvider(factory)
    }

    fun <T : DataProvider> addProvider(factory: PlatformFactory<T>) {
        addProvider<T> { output: FabricDataOutput ->
            factory.create(
                output,
                helper
            )
        }
    }

    fun <T : DataProvider> addProvider(factory: PlatformRegistryDependentFactory<T>) {
        addProvider { output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider> ->
            factory.create(
                output,
                registriesFuture,
                helper
            )
        }
    }

    fun interface PlatformFactory<T : DataProvider> {
        fun create(output: FabricDataOutput, helper: PlatformRecipeHelper): T
    }

    fun interface PlatformRegistryDependentFactory<T : DataProvider> {
        fun create(
            output: FabricDataOutput,
            registriesFuture: CompletableFuture<HolderLookup.Provider>,
            helper: PlatformRecipeHelper
        ): T
    }
}