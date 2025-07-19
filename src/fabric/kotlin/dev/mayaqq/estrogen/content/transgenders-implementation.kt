package dev.mayaqq.estrogen.content

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import uwu.serenity.kritter.client.stdlib.clientOnly
import uwu.serenity.kritter.stdlib.BlockBuilder
import uwu.serenity.kritter.stdlib.ItemBuilder

actual fun ItemBuilder<*>.color(provider: (stack: ItemStack, tint: Int) -> Int) = clientOnly {
    onRegister {
        ColorProviderRegistry.ITEM.register(provider, it)
    }
}

actual fun BlockBuilder<*>.color(provider: (state: BlockState, view: BlockAndTintGetter?, pos: BlockPos?, tint: Int) -> Int) = clientOnly {
    onRegister {
        ColorProviderRegistry.BLOCK.register(provider, it)
    }
}