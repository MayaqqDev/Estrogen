package dev.mayaqq.estrogen.content.fluids.registry

import com.teamresourceful.resourcefullib.client.fluid.registry.ResourcefulClientFluidRegistry
import com.teamresourceful.resourcefullib.common.fluid.registry.ResourcefulFluidRegistry

interface FluidRegistryProvider {
    val fluidRegistry: ResourcefulFluidRegistry
    val clientFluidRegistry: ResourcefulClientFluidRegistry
}