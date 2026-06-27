package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.id
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.item.enchantment.Enchantment

object EstrogenEnchantments : Registrar<Enchantment> by Registrar(MOD_ID, Registries.ENCHANTMENT) {

    val UwUfyingCurse: Enchantment by entry("uwufy_curse", {
        Enchantment.enchantment(
            Enchantment.definition(
                BuiltInRegistries.ITEM.getOrCreateTag(EstrogenTags.Items.HEAD_ENCHANTABLE),
                1,
                1,
                Enchantment.constantCost(25),
                Enchantment.constantCost(50),
                8,
                EquipmentSlotGroup.HEAD
            )
        ).build(id("uwufy_curse"))
    }) {}
}