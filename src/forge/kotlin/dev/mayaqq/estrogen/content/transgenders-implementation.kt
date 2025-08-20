package dev.mayaqq.estrogen.content

import com.mojang.blaze3d.shaders.Program
import dev.mayaqq.estrogen.forge.client.blocksForColors
import dev.mayaqq.estrogen.forge.client.itemsForColors
import net.minecraft.client.renderer.EffectInstance
import net.minecraft.client.renderer.PostChain
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import org.lwjgl.opengl.GL30
import uwu.serenity.kritter.client.stdlib.clientOnly
import uwu.serenity.kritter.stdlib.BlockBuilder
import uwu.serenity.kritter.stdlib.ItemBuilder

actual fun ItemBuilder<*>.color(provider: (stack: ItemStack, tint: Int) -> Int) = clientOnly {
    onRegister {
        itemsForColors.put(it, provider)
    }
}

actual fun BlockBuilder<*>.color(provider: (state: BlockState, view: BlockAndTintGetter?, pos: BlockPos?, tint: Int) -> Int) = clientOnly {
    onRegister {
        blocksForColors.put(it, provider)
    }
}