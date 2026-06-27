@file:EventSubscriber
package dev.mayaqq.estrogen.content.enchantments

import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.server.ServerChatEvent
import dev.mayaqq.cynosure.text.Text.asComponent
import dev.mayaqq.cynosure.utils.`fun`.uwufy
import dev.mayaqq.estrogen.content.EstrogenEnchantments.UwUfyingCurse
import dev.mayaqq.estrogen.utils.holder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.EnchantmentHelper

@Subscription
fun onChatMessage(event: ServerChatEvent) {
    if (EnchantmentHelper.getItemEnchantmentLevel(UwUfyingCurse.holder(), event.player.getItemBySlot(EquipmentSlot.HEAD)) > 0) {
        event.message = event.rawText.uwufy().asComponent()
    }
}
