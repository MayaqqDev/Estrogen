package dev.mayaqq.estrogen.content.fluids.registry

import com.teamresourceful.resourcefullib.common.fluid.ResourcefulBucketItem
import com.teamresourceful.resourcefullib.common.fluid.ResourcefulFlowingFluid
import com.teamresourceful.resourcefullib.common.fluid.ResourcefulLiquidBlock
import com.teamresourceful.resourcefullib.common.fluid.data.FluidData
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.material.Fluid

@Suppress("UnstableApiUsage")
class EstrogenFluidEntry<S : ResourcefulFlowingFluid.Still, F : ResourcefulFlowingFluid.Flowing>(
    key: ResourceKey<in Fluid>,
    val properties: () -> FluidData,
    val flowingEntry: RegistryEntry<F>,
    val blockEntry: RegistryEntry<ResourcefulLiquidBlock>,
    val bucketEntry: RegistryEntry<ResourcefulBucketItem>
) : RegistryEntry<S>(key) {
    val source: ResourcefulFlowingFluid.Still
        get() = this.value as ResourcefulFlowingFluid.Still
    val flowing: ResourcefulFlowingFluid.Flowing
        get() = flowingEntry.value as ResourcefulFlowingFluid.Flowing
    val block: ResourcefulLiquidBlock
        get() = blockEntry.value!!
    val bucket: ResourcefulBucketItem
        get() = bucketEntry.value!!
}