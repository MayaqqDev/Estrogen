package dev.mayaqq.estrogen.api.item.equip

import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

data class SlotInfo(val id: String, val wearer: LivingEntity, val index: Int) {
    fun slotContext(stack: ItemStack): ModifyOnlyContext = ModifyOnlyContext(stack)
}