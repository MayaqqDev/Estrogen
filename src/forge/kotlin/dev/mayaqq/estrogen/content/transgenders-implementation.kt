package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.forge.client.itemsForColors
import net.minecraft.world.item.ItemStack
import uwu.serenity.kritter.client.stdlib.clientOnly
import uwu.serenity.kritter.stdlib.ItemBuilder

actual fun ItemBuilder<*>.color(provider: (stack: ItemStack, tint: Int) -> Int) = clientOnly {
    onRegister {
        itemsForColors.put(it, provider)
    }
}