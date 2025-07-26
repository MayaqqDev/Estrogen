package dev.mayaqq.estrogen.content.fluids.registry

import earth.terrarium.botarium.common.registry.fluid.*
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.material.Fluid
import uwu.serenity.kritter.api.entry.Delegate
import uwu.serenity.kritter.api.entry.RegistryEntry

class EstrogenFluidEntry<S : BotariumSourceFluid, F : BotariumFlowingFluid>(
    key: ResourceKey<in Fluid>,
    holder: Delegate<S>,
    val properties: FluidData,
    val flowingEntry: RegistryEntry<F>,
    val blockEntry: RegistryEntry<BotariumLiquidBlock>,
    val bucketEntry: RegistryEntry<FluidBucketItem>
) : RegistryEntry<S>(key, holder, mutableSetOf()) {
    val source: BotariumSourceFluid
        get() = this.value
    val flowing: BotariumFlowingFluid
        get() = flowingEntry.value
    val block: BotariumLiquidBlock
        get() = blockEntry.value
    val bucket: FluidBucketItem
        get() = bucketEntry.value
}