package dev.mayaqq.estrogen.content.fluids.item

import earth.terrarium.common_storage_lib.context.ItemContext
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage
import earth.terrarium.common_storage_lib.fluid.util.FluidStorageData
import net.minecraft.core.component.DataComponentType

class EstrogenItemFluidStorage(context: ItemContext, componentType: DataComponentType<FluidStorageData>) : SimpleFluidStorage(context, componentType, 1, 0) {
    init {
        val oldSlot = this.slots[0]
        val newSlot = EstrogenFluidSlot(
            getAdrianField("update"),
            getAdrianField("save")
        )
        newSlot.readSnapshot(oldSlot.createSnapshot())
        this.slots[0] = newSlot
    }

    private fun getAdrianField(name: String) = (EstrogenItemFluidStorage::class.java.getDeclaredField(name).apply {
            isAccessible = true
        }.get(this) as Runnable)
}