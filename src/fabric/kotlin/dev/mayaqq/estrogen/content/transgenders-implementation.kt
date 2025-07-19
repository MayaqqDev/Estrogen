package dev.mayaqq.estrogen.content

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.world.item.ItemStack
import uwu.serenity.kritter.client.stdlib.clientOnly
import uwu.serenity.kritter.stdlib.ItemBuilder

actual fun ItemBuilder<*>.color(provider: (stack: ItemStack, tint: Int) -> Int) = clientOnly {
    onRegister {
        ColorProviderRegistry.ITEM.register(provider, it)
    }
}