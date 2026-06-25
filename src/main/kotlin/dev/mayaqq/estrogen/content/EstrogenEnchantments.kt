package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.enchantments.UwuCurseEnchantment
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.Enchantment

object EstrogenEnchantments : Registrar<Enchantment> by Registrar(MOD_ID, Registries.ENCHANTMENT) {
    val UwUfyingCurse by entry("uwufy_curse", { UwuCurseEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD)}) {}
}