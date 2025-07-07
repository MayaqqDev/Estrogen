package dev.mayaqq.estrogen.content.items

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block

class DreamCatcherItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    fun setDyes(stack: ItemStack, left: Int?, middle: Int?, right: Int?) {
        val colors = CompoundTag()
        colors.putInt("left", left ?: -1)
        colors.putInt("middle", middle ?: -1)
        colors.putInt("right", right ?: -1)

        stack.orCreateTag.put("colors", colors)
    }

    fun left(stack: ItemStack): Int? {
        return stack.orCreateTag.getCompound("colors")?.getInt("left")
    }

    fun middle(stack: ItemStack): Int? {
        return stack.orCreateTag.getCompound("colors")?.getInt("middle")
    }

    fun right(stack: ItemStack): Int? {
        return stack.orCreateTag.getCompound("colors")?.getInt("right")
    }

    fun getColor(stack: ItemStack, tintIndex: Int): Int {
        return stack.tag?.getCompound("colors")?.let {
            when (tintIndex) {
                1 -> stack.orCreateTag.getCompound("colors").getInt("left")
                2 -> stack.orCreateTag.getCompound("colors").getInt("middle")
                3 -> stack.orCreateTag.getCompound("colors").getInt("right")
                else -> -1
            }
        } ?: -1
    }

    companion object {
        fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
            val item = stack.item as DreamCatcherItem
            return item.getColor(stack, tintIndex)
        }
    }
}