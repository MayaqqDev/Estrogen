package dev.mayaqq.estrogen.content.items

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block

class DreamCatcherItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    fun setDyes(stack: ItemStack, left: DyeColor?, middle: DyeColor?, right: DyeColor?) {
        val colors = CompoundTag()
        colors.putString("left", left?.name ?: "empty")
        colors.putString("middle", middle?.name ?: "empty")
        colors.putString("right", right?.name ?: "empty")

        stack.orCreateTag.put("colors", colors)
    }
}