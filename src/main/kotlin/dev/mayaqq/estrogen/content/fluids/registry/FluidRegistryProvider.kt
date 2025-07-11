package dev.mayaqq.estrogen.content.fluids.registry

import earth.terrarium.botarium.common.registry.fluid.FluidRegistry

interface FluidRegistryProvider {
    fun getFluidRegistry(): FluidRegistry
}