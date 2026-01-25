@file:EventSubscriber
package dev.mayaqq.estrogen.content.enchantments

import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.server.ServerChatEvent
import dev.mayaqq.cynosure.text.Text.asComponent
import dev.mayaqq.cynosure.utils.`fun`.uwufy
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenEnchantments.UwUfyingCurse
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentCategory
import net.minecraft.world.item.enchantment.EnchantmentHelper

class UwuCurseEnchantment(rarity: Rarity, category: EnchantmentCategory, vararg slots: EquipmentSlot) : Enchantment(rarity, category, slots) {
    override fun getMinCost(level: Int): Int = 25
    override fun getMaxCost(level: Int): Int = 50
    override fun isTreasureOnly(): Boolean = true
    override fun isCurse(): Boolean = true
}

@Subscription
fun onChatMessage(event: ServerChatEvent) {
    if (EnchantmentHelper.getEnchantments(event.player.getItemBySlot(EquipmentSlot.HEAD)).containsKey(UwUfyingCurse)) {
        event.message = event.rawText.uwufy().asComponent()
    }
}
