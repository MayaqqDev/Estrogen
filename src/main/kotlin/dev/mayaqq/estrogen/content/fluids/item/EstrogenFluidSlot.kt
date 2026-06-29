package dev.mayaqq.estrogen.content.fluids.item

import dev.mayaqq.estrogen.content.EstrogenFluids
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidSlot
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts

class EstrogenFluidSlot(update: Runnable, save: Runnable) : SimpleFluidSlot.Filtered(0, update, save, {it.isOf(EstrogenFluids.LiquidEstrogen.source)}) {
    override fun getLimit(fluid: FluidResource): Long = FluidAmounts.BUCKET
}