@file:EventSubscriber
package dev.mayaqq.estrogen.compat.cobblemon

import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.player.interaction.InteractionEvent
import dev.mayaqq.estrogen.compat.cobblemon.CobblemonCompat.changeGender
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.items.GenderChangePotionItem
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items


@Subscription
fun onEntityInteract(event: InteractionEvent.UseEntity) {
    if (!isModLoaded("cobblemon")) return

    if (event.entity.javaClass.packageName.contains("cobblemon")) {
        if (event.getUsedStack().`is`(EstrogenItems.GenderChangePotion)) {
            if (changeGender(event.entity)) {
                event.getUsedStack().shrink(1)
                val itemStack = ItemStack(Items.GLASS_BOTTLE)
                if (!event.player.inventory.add(itemStack)) {
                    event.player.drop(itemStack, false)
                }
                GenderChangePotionItem.playParticles(event.level, event.entity as LivingEntity)
                event.result = InteractionResult.SUCCESS
            }
        }
    }
}