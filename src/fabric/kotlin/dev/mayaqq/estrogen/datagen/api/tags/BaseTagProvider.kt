package dev.mayaqq.estrogen.datagen.api.tags


import dev.mayaqq.estrogen.datagen.api.platform.Platform
import dev.mayaqq.estrogen.datagen.api.platform.PlatformHelper
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import java.util.concurrent.CompletableFuture

abstract class BaseTagProvider<T>(
    output: FabricDataOutput,
    registryKey: ResourceKey<Registry<T>>,
    registriesFuture: CompletableFuture<HolderLookup.Provider>,
    private val helper: PlatformHelper
) : FabricTagProvider<T>(output, registryKey, registriesFuture) {
    protected val platform: Platform = this.helper.platform

    override fun getName(): String {
        return this.helper.name("Estrogen's Tags for " + this.registryKey.location())
    }

    abstract class ItemProvider(
        output: FabricDataOutput,
        registriesFuture: CompletableFuture<HolderLookup.Provider>,
        helper: PlatformHelper
    ) : BaseTagProvider<Item>(output, Registries.ITEM, registriesFuture, helper) {
        override fun reverseLookup(element: Item): ResourceKey<Item> {
            return element.builtInRegistryHolder().key()
        }
    }

    abstract class BlockProvider(
        output: FabricDataOutput,
        registriesFuture: CompletableFuture<HolderLookup.Provider>,
        helper: PlatformHelper
    ) : BaseTagProvider<Block>(output, Registries.BLOCK, registriesFuture, helper) {
        override fun reverseLookup(element: Block): ResourceKey<Block> {
            return element.builtInRegistryHolder().key()
        }
    }

    abstract class FluidProvider(
        output: FabricDataOutput,
        registriesFuture: CompletableFuture<HolderLookup.Provider>,
        helper: PlatformHelper
    ) : BaseTagProvider<Fluid>(output, Registries.FLUID, registriesFuture, helper) {
        override fun reverseLookup(element: Fluid): ResourceKey<Fluid> {
            return element.builtInRegistryHolder().key()
        }
    }

    abstract class EntityProvider(
        output: FabricDataOutput,
        registriesFuture: CompletableFuture<HolderLookup.Provider>,
        helper: PlatformHelper
    ) : BaseTagProvider<EntityType<*>>(output, Registries.ENTITY_TYPE, registriesFuture, helper) {
        override fun reverseLookup(element: EntityType<*>): ResourceKey<EntityType<*>> {
            return element.builtInRegistryHolder().key()
        }
    }
}