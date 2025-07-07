package dev.mayaqq.estrogen.content.items

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block

class DreamCatcherItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    fun setDyes(stack: ItemStack, left: Int, middle: Int, right: Int) {
        stack.orCreateTag.putInt(COLOR_LEFT, left)
        stack.orCreateTag.putInt(COLOR_MIDDLE, middle)
        stack.orCreateTag.putInt(COLOR_RIGHT, right)
    }

    fun isBlank(stack: ItemStack): Boolean {
        return left(stack) == null
    }

    fun left(stack: ItemStack): Int? = getColor(stack, COLOR_LEFT)
    fun middle(stack: ItemStack): Int? = getColor(stack, COLOR_MIDDLE)
    fun right(stack: ItemStack): Int? = getColor(stack, COLOR_RIGHT)

    fun getColor(stack: ItemStack, color: String): Int? {
        return if (stack.orCreateTag.contains(color)) {
            stack.orCreateTag.getInt(color)
        } else null
    }

    fun getColor(stack: ItemStack, tintIndex: Int): Int {
        return when (tintIndex) {
            1 -> left(stack)?: -1
            2 -> middle(stack)?: -1
            3 -> right(stack)?: -1
            else -> -1
        }
    }

    companion object {
        const val COLOR_LEFT = "colorLeft"
        const val COLOR_MIDDLE = "colorMiddle"
        const val COLOR_RIGHT = "colorRight"

        fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
            val item = stack.item as DreamCatcherItem
            return item.getColor(stack, tintIndex)
        }
    }
}