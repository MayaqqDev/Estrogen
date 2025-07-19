package dev.mayaqq.estrogen.content.items

import dev.mayaqq.estrogen.utils.TriColor
import dev.mayaqq.estrogen.utils.getTriColor
import dev.mayaqq.estrogen.utils.putTriColor
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block

class DreamCatcherItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    fun setDyes(stack: ItemStack, triColor: TriColor) {
        stack.orCreateTag.putTriColor(triColor)
    }
    fun isBlank(stack: ItemStack): Boolean = !stack.orCreateTag.contains("colors")

    fun triColor(stack: ItemStack): TriColor? {
        return if (stack.orCreateTag.contains("colors")) stack.orCreateTag.getTriColor() else null
    }

    fun getColor(stack: ItemStack, tintIndex: Int): Int {
        return when (tintIndex) {
            1 -> triColor(stack)?.left?.toInt()?: -1
            2 -> triColor(stack)?.middle?.toInt()?: -1
            3 -> triColor(stack)?.right?.toInt()?: -1
            else -> -1
        }
    }

    companion object {
        fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
            val item = stack.item as DreamCatcherItem
            return item.getColor(stack, tintIndex)
        }
    }
}