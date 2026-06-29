package dev.mayaqq.estrogen.api.item.equip

import net.minecraft.world.entity.LivingEntity

data class SlotInfo(val id: String, val wearer: LivingEntity, val index: Int)