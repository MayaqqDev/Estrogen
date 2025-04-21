package dev.mayaqq.estrogen.content.enchantments

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentCategory

class UwuCurseEnchantment(rarity: Rarity, category: EnchantmentCategory, vararg slots: EquipmentSlot) : Enchantment(rarity, category, slots) {
    override fun getMinCost(level: Int): Int = 25
    override fun getMaxCost(level: Int): Int = 50
    override fun isTreasureOnly(): Boolean = true
    override fun isCurse(): Boolean = true
}