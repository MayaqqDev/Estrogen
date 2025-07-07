package dev.mayaqq.estrogen.utils

import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.minecraft.diffuseColor
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.ItemStack

class TriColor(val left: Color, val middle: Color, val right: Color) {

    fun mix(triColor: TriColor): TriColor {
        return TriColor(
            this.left.mix(triColor.left),
            this.middle.mix(triColor.middle),
            this.right.mix(triColor.right))
    }

    companion object {
        fun fromDyes(dyes: List<ItemStack>): TriColor {
            return TriColor(colorFromDye(dyes[0]), colorFromDye(dyes[1]), colorFromDye(dyes[2]))
        }

        private fun mixColorWithDye(original: Color, dyeStack: ItemStack): Color {
            if (dyeStack.isEmpty || dyeStack.item !is DyeItem) return original
            val dyeColor: Color = colorFromDye(dyeStack)
            return original.mix(dyeColor, .5f)
        }

        private fun colorFromDye(dyeStack: ItemStack): Color {
            return (dyeStack.item as DyeItem).dyeColor.diffuseColor
        }
    }
}

fun CompoundTag.putTriColor(triColor: TriColor) {
    val tag = CompoundTag()
    tag.putInt("left", triColor.left.toInt())
    tag.putInt("middle", triColor.middle.toInt())
    tag.putInt("right", triColor.right.toInt())
    this.put("colors", tag)
}

fun CompoundTag.getTriColor(): TriColor? {
    if (this.contains("colors")) {
        val colors = this.getCompound("colors")
        return TriColor(
            Color(colors.getInt("left")),
            Color(colors.getInt("middle")),
            Color(colors.getInt("right"))
        )
    } else return null
}