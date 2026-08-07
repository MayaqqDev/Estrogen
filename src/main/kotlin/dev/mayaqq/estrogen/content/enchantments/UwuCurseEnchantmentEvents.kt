package dev.mayaqq.estrogen.content.enchantments

import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.server.ServerChatEvent
import dev.mayaqq.cynosure.text.Text.asComponent
import dev.mayaqq.cynosure.utils.`fun`.uwufy
import dev.mayaqq.estrogen.content.EstrogenEnchantments.UwUfyingCurse
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.EnchantmentHelper
import kotlin.jvm.optionals.getOrNull

object UwuCurseEnchantmentEvents {
    @Subscription
    fun onChatMessage(event: ServerChatEvent) {
        val registry = event.player.registryAccess().registry(Registries.ENCHANTMENT).getOrNull()?: return
        val enchantment = registry.getHolder(UwUfyingCurse).getOrNull()?: return

        if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, event.player.getItemBySlot(EquipmentSlot.HEAD)) > 0) {
            event.message = event.rawText.uwufy().asComponent()
        }
    }
}
